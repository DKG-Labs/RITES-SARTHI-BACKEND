package com.sarthi.service.Impl;

import com.sarthi.constant.AppConstant;
import com.sarthi.dto.WorkflowDto;
import com.sarthi.dto.WorkflowDtos.TransitionActionReqDto;
import com.sarthi.dto.WorkflowDtos.TransitionDto;
import com.sarthi.dto.WorkflowDtos.WorkflowTransitionDto;
import com.sarthi.entity.*;
import com.sarthi.entity.rawmaterial.InspectionCall;
import com.sarthi.exception.BusinessException;
import com.sarthi.exception.ErrorDetails;
import com.sarthi.exception.InvalidInputException;
import com.sarthi.repository.*;
import com.sarthi.repository.rawmaterial.InspectionCallRepository;
import com.sarthi.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    @Autowired
    private WorkflowMasterRepository workflowMasterRepository;
    @Autowired
    private UserMasterRepository userMasterRepository;
    @Autowired
    private TransitionMasterRepository transitionMasterRepository;
    @Autowired
    private WorkflowTransitionRepository workflowTransitionRepository;
    @Autowired
    private RoleMasterRepository roleMasterRepository;
    @Autowired
    private PincodeClusterRepository pincodeClusterRepository;
    @Autowired
    private ClusterRioUserRepository clusterRioUserRepository;
    @Autowired
    private RegionClusterRepository regionClusterRepository;
    @Autowired
    private ClusterPrimaryIeRepository clusterPrimaryIeRepository;
    @Autowired
    private ClusterSecondaryIeRepository clusterSecondaryIeRepository;
    @Autowired
    private TransitionConditionMasterRepository transitionConditionMasterRepository;
    @Autowired
    private ClusterCmUserRepository clusterCmUserRepository;
    @Autowired
    private ProcessIeMasterRepository processIeMasterRepository;
    @Autowired
    private ProcessIeMappingRepository processIeMappingRepository;
    @Autowired
    private RegionSbuHeadRepository regionSbuHeadRepository;
    @Autowired
    private InspectionCallRepository inspectionCallRepository;

    @Autowired
    private IeFieldsMappingRepository ieFieldsMappingRepository;
    @Autowired
    private PincodePoIMappingRepository pincodePoIMappingRepository;
    @Autowired
    private IePincodePoiMappingRepository iePincodePoiMappingRepository;
@Autowired
    private ieControllingManagerRepository ieControllingManagerRepository;




    public void validateUser(Integer userId) {
        UserMaster userMaster = userMasterRepository.findById(userId).orElseThrow(() -> new InvalidInputException(new ErrorDetails(AppConstant.USER_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                AppConstant.ERROR_TYPE_VALIDATION, "User not found.")));
    }
    @Override
    @Transactional
    public WorkflowTransitionDto initiateWorkflow(String requestId, Integer createdBy, String workflowName, String pincode) {


        // Validate vendor
      //  userService.validateUser(createdBy);
        validateUser(createdBy);
        // Fetch workflow
         WorkflowMaster workflow = workflowMasterRepository.findByWorkflowName(workflowName);

        if (Objects.isNull(workflow)) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Workflow not found"
                    )
            );
        }

      //  TransitionMaster transition = null;

        // Fetch initial transition
        TransitionMaster transition = transitionMasterRepository
                .findByWorkflowIdAndTransitionOrder(workflow.getWorkflowId(), 1)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Initial transition not found"
                        )
                ));
        // Prevent duplicate workflow
        WorkflowTransition exists = workflowTransitionRepository
                .findByWorkflowIdAndRequestId(workflow.getWorkflowId(), requestId);
      if (Objects.nonNull(exists)) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Workflow already initiated for this request"
                    )
            );
        }
        // If IE is scheduling IC → start workflowId=2
        if (workflowName.equalsIgnoreCase("IE INSPECTION")) {

              //  WorkflowTransition last =
                //        workflowTransitionRepository.findTopByRequestIdOrderByWorkflowTransitionIdDesc(requestId);
            WorkflowTransition last =
                    workflowTransitionRepository
                            .findTopByRequestIdAndStatusOrderByWorkflowTransitionIdDesc(requestId, "CALL_REGISTERED");

            System.out.println("Last CALL_REGISTERED record: " + last);

            System.out.print(last);
         //    String inspectionType ="PROCESS";
            String inspectionType ="Raw Material";
               if(inspectionType.equalsIgnoreCase("PROCESS") && last.getNextRoleName().equalsIgnoreCase("IE")){
                   validateProcessIeAction(last.getProcessIeUserId(),createdBy);
               }else {
                   if (last.getAssignedToUser() == null ||
                           !last.getAssignedToUser().equals(createdBy)) {// Only assigned IE can act

                       throw new InvalidInputException(
                               new ErrorDetails(
                                       AppConstant.ACCESS_DENIED,
                                       AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                       AppConstant.ERROR_TYPE_VALIDATION,
                                       "You are not authorized to act on this inspection call."
                               )
                       );
                   }
               }

            // Fetch first transition of IE workflow
            TransitionMaster transitionMaster = transitionMasterRepository
                    .findByWorkflowIdAndTransitionOrder(workflow.getWorkflowId(), 1)
                    .orElseThrow(() -> new BusinessException(
                            new ErrorDetails(
                                    AppConstant.ERROR_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_VALIDATION,
                                    "Initial IE transition not found"
                            )
                    ));

            System.out.print(transitionMaster);
            WorkflowTransition entry = new WorkflowTransition();
            entry.setWorkflowId(workflow.getWorkflowId());
            entry.setTransitionId(transition.getTransitionId());
            entry.setRequestId(requestId);
            entry.setStatus("IE_SCHEDULED");
            entry.setAction("IE_SCHEDULE_CALL");
            entry.setRemarks("IE has scheduled the call");
            entry.setCreatedBy(createdBy);
            entry.setCreatedDate(new Date());
            entry.setCurrentRole(String.valueOf(transitionMaster.getCurrentRoleId()));
            entry.setNextRole(String.valueOf(transitionMaster.getNextRoleId()));
            entry.setCurrentRoleName(roleNameById(transitionMaster.getCurrentRoleId()));
            entry.setNextRoleName(roleNameById(transitionMaster.getNextRoleId()));
            entry.setAssignedToUser(createdBy);
            entry.setJobStatus("ASSIGNED");
            entry.setProcessIeUserId(last.getProcessIeUserId());
            entry.setWorkflowSequence(last.getWorkflowSequence()+1);
            workflowTransitionRepository.save(entry);

            return mapWorkflowTransition(entry);
        }

        Integer assignedRioUserId =null;
        String rio = null;


          if (workflowName.equalsIgnoreCase("INSPECTION CALL")) {

              // Step 1: Get cluster by pincode
           /*   PincodeCluster cluster = pincodeClusterRepository.findByPincode(pincode)
                      .orElseThrow(() -> new BusinessException(
                              new ErrorDetails(
                                      AppConstant.ERROR_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_VALIDATION,
                                      "No cluster found for pincode")));

              // Step 2: Get region for that cluster
              RegionCluster region = regionClusterRepository.findByClusterName(cluster.getClusterName())
                      .orElseThrow(() -> new BusinessException(
                              new ErrorDetails(
                                      AppConstant.ERROR_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_VALIDATION,
                               "No region found for cluster")
                      ));

              // Find RIO user for that region
              ClusterRioUser rioUser = clusterRioUserRepository.findByClusterName(cluster.getClusterName())
                      .orElseThrow(() -> new BusinessException(
                              new ErrorDetails(
                                      AppConstant.ERROR_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                      AppConstant.ERROR_TYPE_VALIDATION,
                         "No RIO user found for region")
                      ));*/

              InspectionCall ic = inspectionCallRepository.findByIcNumber(requestId)
                      .orElseThrow(() -> new BusinessException(
                              new ErrorDetails(
                                      AppConstant.ERROR_CODE_INVALID,
                                      AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                      AppConstant.ERROR_TYPE_VALIDATION,
                                      "Place of Inspection (POI) is not assigned"
                              )
                      ));

              //  PincodePoIMapping poi = pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection());


              PincodePoIMapping poi =
                      pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection())
                              .orElseThrow(() -> new BusinessException(
                                      new ErrorDetails(
                                              AppConstant.ERROR_CODE_RESOURCE,
                                              AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                              AppConstant.ERROR_TYPE_VALIDATION,
                                              "Invalid POI code"
                                      )
                              ));
              String stage = null;
              if(ic.getTypeOfCall().equalsIgnoreCase("Raw Material")){
                  stage ="R";
              }
              String product ="ERC";
              String pinCode = poi.getPinCode();

              IEFieldsMapping mapping =
                      ieFieldsMappingRepository
                              .findByPinCodeProductAndStageMatch(pinCode, product, stage)
                              .orElseThrow(() -> new BusinessException(
                                      new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                              AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                              AppConstant.ERROR_TYPE_VALIDATION,
                                              "No IE mapping for given pin/product/stage")));

              rio =mapping.getRio();

            //  assignedRioUserId = rioUser.getRioUserId();
          }

        // Create transition entry
        WorkflowTransition entry = new WorkflowTransition();
        entry.setWorkflowId(workflow.getWorkflowId());
        entry.setTransitionId(transition.getTransitionId());
        entry.setRequestId(requestId);
        entry.setStatus(AppConstant.CREATED_TYPE);
        entry.setJobStatus(AppConstant.CREATED_TYPE);
        //entry.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
        entry.setCurrentRoleName(roleNameById(transition.getCurrentRoleId()));
        entry.setNextRole(String.valueOf(transition.getNextRoleId()));
        entry.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
        entry.setNextRoleName(roleNameById(transition.getNextRoleId()));
      //  entry.setNextRole(String.valueOf(transition.getNextRoleId()));
        entry.setCreatedBy(createdBy);
        entry.setCreatedDate(new Date());
        entry.setWorkflowSequence(1);
        //entry.setTransitionOrder(1);

        if(workflow.getWorkflowName().equalsIgnoreCase("INSPECTION CALL")){
            entry.setAssignedToUser(assignedRioUserId);
            entry.setRio(rio);
        }

        workflowTransitionRepository.save(entry);

        return mapWorkflowTransition(entry);
    }
    private void validateProcessIeAction(Integer processIeUserId, Integer actionBy) {


        List<ProcessIeMapping> mappings =
                processIeMappingRepository.findByProcessIeUserId(processIeUserId);

        if (mappings.isEmpty()) {
            throw new BusinessException(
                    new ErrorDetails(
                            AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "No IE users mapped under Process IE user: " + processIeUserId)
            );
        }

        List<Integer> allowedIeUsers = mappings.stream()
                .map(ProcessIeMapping::getIeUserId)
                .collect(Collectors.toList());
        allowedIeUsers.add(processIeUserId);
        System.out.print("users"+ allowedIeUsers);

        //  If actionBy is NOT part of allowed list Reject
        if (!allowedIeUsers.contains(actionBy)) {
            throw new InvalidInputException(
                    new ErrorDetails(
                            AppConstant.ACCESS_DENIED,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "You are not authorized to perform this action. " +
                                    "User " + actionBy + " is not under Process IE user " + processIeUserId
                    )
            );
        }
    }


    @Override
    @Transactional
    public WorkflowTransitionDto  performTransitionAction(TransitionActionReqDto req) {

       // String userId = CommonUtils.getUserIdFromAuthHeader(authorizationHeader);

        // Validate user
       validateUser(req.getActionBy());

       System.out.print(req);
        // Fetch current workflow transition
       WorkflowTransition current = workflowTransitionRepository
                .findById(req.getWorkflowTransitionId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Workflow transition not found"
                        )
                ));

            TransitionMaster transition = transitionMasterRepository
                    .findById(current.getTransitionId())
                    .orElseThrow(() -> new BusinessException(
                            new ErrorDetails(
                                    AppConstant.ERROR_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_VALIDATION,
                                    "transition not found"
                            )
                    ));




        // IE WORKFLOW (ID = 2)
        if (current.getWorkflowId() == 2) {



         //   TransitionMaster nextTransition = transitionMasterRepository.findByTransitionName(req.getAction());
          //  System.out.print(nextTransition);
            WorkflowTransition last =null;
          if(req.getAction().equalsIgnoreCase("INITIATE_INSPECTION")){
          last = workflowTransitionRepository
                              .findTopByRequestIdAndStatusOrderByWorkflowTransitionIdDesc(req.getRequestId(), "IE_SCHEDULED");
          }else{
            last = workflowTransitionRepository
                      .findTopByRequestIdOrderByWorkflowTransitionIdDesc(current.getRequestId());

          }

//            String inspectionType ="PROCESS";
            String inspectionType ="Raw Material";
            if(inspectionType.equalsIgnoreCase("PROCESS")){
                validateProcessIeAction(last.getProcessIeUserId(),req.getActionBy());
            }else if (last.getAssignedToUser() == null ||
                    !last.getAssignedToUser().equals(req.getActionBy())) {

                throw new InvalidInputException(
                        new ErrorDetails(
                                AppConstant.ACCESS_DENIED,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "You are not authorized to act on this inspection call."
                        )
                );
            }
            if (req.getAction().equalsIgnoreCase("CANCEL_CALL")) {
                return cancelCallByIE(current, req);
            }

            if (req.getAction().startsWith("IE_REQUEST_RESCHEDULE")
                    || req.getAction().startsWith("CM_APPROVE_RESCHEDULE")
                    || req.getAction().startsWith("CM_REJECT_RESCHEDULE")
                    || req.getAction().startsWith("CM_FORWARD_TO_SBU_HEAD")
                    || req.getAction().startsWith("SBU_HEAD_APPROVE_RESCHEDULE")
                    || req.getAction().startsWith("SBU_HEAD_REJECT_RESCHEDULE")) {

                TransitionMaster nextTransition = resolveConditionalTransition(current, req);

                WorkflowTransition next = createNextTransition(
                        current,
                        nextTransition,
                        req.getAction(),
                        req.getRemarks(),
                        req
                );
                assignRescheduleUser(next, current, req);

                workflowTransitionRepository.save(next);
                return mapWorkflowTransition(next);
            }

            if(req.getAction().equalsIgnoreCase("CONFIRM_CANCEL_AFTER_PAYMENT")){
                return confirmCancelAfterPayment(current,req);
            }

            if(req.getActionBy().equals("INITIATE_INSPECTION")){
                // AUTO EXPIRE ANY PENDING QTY EDIT REQUEST
                WorkflowTransition pendingQty = workflowTransitionRepository
                        .findTopByRequestIdAndStatus(current.getRequestId(), "QTY_EDIT_REQUESTED");

                if (pendingQty != null) {
                    System.out.println("Qty edit request expired automatically because inspection started.");
                    // Optional: update status in DB
                    pendingQty.setStatus("QTY_EDIT_EXPIRED");
                    workflowTransitionRepository.save(pendingQty);
                }
            }
            // If action is NOT VERIFY_PO_DETAILS then check whether last action was VERIFY_PO_DETAILS
            if (req.getAction().equalsIgnoreCase("ENTER_SHIFT_DETAILS_AND_START_INSPECTION")) {

                if (last == null || !last.getAction().equalsIgnoreCase("VERIFY_PO_DETAILS")) {

                    throw new BusinessException(
                            new ErrorDetails(
                                    AppConstant.INVALID_WORKFLOW_TRANSITION,
                                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                    AppConstant.ERROR_TYPE_VALIDATION,
                                    "Please verify PO details before proceeding."
                            )
                    );
                }

                // Auto-Set PoStatus OK
               req.setPoStatus("OK");
            }

            TransitionMaster nextTransition = resolveConditionalTransition(current, req);
            if (nextTransition == null) {
                throw new BusinessException(new ErrorDetails(
                        AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "No transition could be resolved for action: " + req.getAction()
                ));
            }
            WorkflowTransition next = createNextTransition(
                    current,
                    nextTransition,
                    req.getAction() == null ? "DONE" : req.getAction(),
                    req.getRemarks(),
                    req
            );

            if (req.getAction().equalsIgnoreCase("VERIFY_MATERIAL_AVAILABILITY")
                    && "NO".equalsIgnoreCase(req.getMaterialAvailable())) {

                String paymentType = "PAYABLE";
                boolean paymentDone = true;

                if (paymentType.equalsIgnoreCase("NON_PAYABLE")) {
                    next.setStatus("CANCELLED");
                    next.setRemarks("Cancelled - Material Not Available");
                }
                else if (!paymentDone) {
                    next.setStatus("BLOCKED");
                    next.setRemarks("Blocked - Payment Pending + No Material");
                }
                else {
                    next.setStatus("CANCELLED");
                    next.setRemarks("Cancelled - Payment Done + No Material");
                }
            }
            if (req.getAction().equalsIgnoreCase("ENTRY_INSPECTION_RESULTS")
                    && "NOT OK".equalsIgnoreCase(req.getMaterialAvailable())) {

                if(req.getMaterialName().equalsIgnoreCase("Raw material") || req.getMaterialName().equalsIgnoreCase("Final")){
                    String paymentType = "PAYABLE";
                    boolean paymentDone = true;

                    if (paymentType.equalsIgnoreCase("PAYABLE") && paymentDone) {
                        next.setStatus(AppConstant.REJECT_TYPE);
                        next.setRemarks("Rejected - Material Not Available");
                    }
                    else if (!paymentDone) {
                        next.setStatus("BLOCKED");
                        next.setRemarks("Blocked - Payment Pending + No Material");
                    }
                    else {
                        next.setStatus(AppConstant.REJECT_TYPE);
                        next.setRemarks("Rejected - Payment Done + No Material");
                    }
                }

            }




            // If next role is CM Assign CM user
            //CHANGE HERE
            if (req.getAction().equalsIgnoreCase("REQUEST_CORRECTION_TO_CM") && roleNameById(nextTransition.getNextRoleId()).equalsIgnoreCase("Control Manager")) {

                Integer ieUserId = current.getAssignedToUser() != null
                        ? current.getAssignedToUser()
                        : req.getActionBy();

             //   Integer cmUserId = getCmUserFromIeUser(ieUserId);
                Optional<UserMaster> um = userMasterRepository.findByUserId(ieUserId);

                Integer cmUserId =null;
                if(um.isPresent()){
                    UserMaster u = um.get();
                    cmUserId =  getCmUserFromIeEmployeeCode(u.getEmployeeCode());
                }

//                if (cmUserId == null) {
//                    throw new BusinessException(
//                            new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
//                                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
//                                    AppConstant.ERROR_TYPE_VALIDATION,
//                                    "No CM user found for IE cluster")
//                    );
//                }


                next.setAssignedToUser(cmUserId);
            }
            if (roleNameById(nextTransition.getCurrentRoleId()).equalsIgnoreCase("Control Manager")) {
                next.setAssignedToUser(current.getCreatedBy());
            }



            workflowTransitionRepository.save(next);
            return mapWorkflowTransition(next);

        }



        if (current.getAssignedToUser() != null &&
                current.getNextRoleName().equalsIgnoreCase("RIO Help Desk")) {

            if (!current.getAssignedToUser().equals(req.getActionBy())) {

                throw new InvalidInputException(
                        new ErrorDetails(
                                AppConstant.ACCESS_DENIED,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "You are not authorized to act on this inspection call."
                        )
                );
            }
        }



        String action = req.getAction().toUpperCase();

        switch (action) {

            case "VERIFY":
                return verifyCall(current, req);

            case "RETURN_TO_VENDOR":
                return returnToVendor(current, req);

            case "FIX_ROUTING":
                return fixRouting(current, req);
            case "CREATE_CALL":
                return vendorResubmit(current, req);

            case "CM_RETURN_TO_IE":
                return cmReturnToIe(current, req);
            case "PAYMENT_VERIFICATION":
                return handleFinancePaymentVerification(current, req);

            case "PARKED_PAYMENT_NOT_RECEIVED":
                return handlePaymentNotReceived(current, req);
            case "CALL_REGISTERED":
                return handlePaymentReceived(current, req);
            case "PAYMENT_CONFIRMED_CANCEL":   // when po verification stage try cancle but blocked due payment then use this trastion to cancle
                return confirmCancelAfterPayment(current, req);
            case "REQUEST_QTY_EDIT":
                return requestQtyEdit(current, req);   //if vendor want to request for qty chnage of IC

            case "CM_QTY_DECISION":
                return cmQtyDecision(current, req);  // cm decision after vendor request for qty change of IC




            default:
                throw new InvalidInputException(new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Invalid action:" +action));

        }
    }

    private WorkflowTransitionDto confirmCancelAfterPayment(WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster t =
                transitionMasterRepository.findByTransitionName("CONFIRM_CANCEL_AFTER_PAYMENT");

        WorkflowTransition next = createNextTransition(
                current,
                t,
                "CANCELLED",
                "Payment received - Call fully cancelled",
                req
        );

        next.setJobStatus("CANCELLED");

        workflowTransitionRepository.save(next);
        return mapWorkflowTransition(next);
    }



    private WorkflowTransitionDto cancelCallByIE(WorkflowTransition current, TransitionActionReqDto req) {
    System.out.print(current);
        if (!roleNameById(Integer.valueOf(current.getNextRole()))
                .equalsIgnoreCase("IE") && !roleNameById(Integer.valueOf(current.getNextRole()))
                .equalsIgnoreCase("Process IE")) {
            throw new InvalidInputException(
                    new ErrorDetails(AppConstant.ACCESS_DENIED,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Only IE can cancel the call")
            );
        }

       /* String paymentType = req.getPaymentType();
        boolean paymentCompleted =
                req.getPaymentCompleted() != null &&
                        req.getPaymentCompleted().equalsIgnoreCase("true");
*/
        String paymentType = "PAYABLE";
        Boolean paymentCompleted = false;

        String status;
        String jobStatus;

        if ("NON_PAYABLE".equalsIgnoreCase(paymentType)) {
            status = "CANCELLED";
            jobStatus = "CANCELLED";
        }
        else if ("PAYABLE".equalsIgnoreCase(paymentType) && !paymentCompleted) {
            status = "BLOCKED";
            jobStatus = "BLOCKED";
        }
        else { // PAYABLE + Completed
            status = "CANCELLED";
            jobStatus = "CANCELLED";
        }


        TransitionMaster transition =
                transitionMasterRepository.findByTransitionNameAndWorkflowId(
                        "CANCEL_CALL",
                        current.getWorkflowId()
                );

        WorkflowTransition next = createNextTransition(
                current,
                transition,
                status,
                req.getRemarks() == null ? "Call cancelled by IE" : req.getRemarks(),
                req
        );

        next.setJobStatus(jobStatus);

        workflowTransitionRepository.save(next);
        return mapWorkflowTransition(next);
    }

private WorkflowTransitionDto verifyCall(WorkflowTransition current, TransitionActionReqDto req) {

    TransitionMaster verifyTransition =
            transitionMasterRepository.findByTransitionName("VERIFY_CALL");

    WorkflowTransition verified = createNextTransition(
            current, verifyTransition, "VERIFIED", "Inspection Call Verified", req
    );
    workflowTransitionRepository.save(verified);

    String inspectionType ="RAW_MATERIAL";
   // String  inspectionType ="PROCESS";
   // String inspectionType ="PROCESS";
    // Get actual inspection type
  //  String inspectionType = req.getInspectionType(); // RAW_MATERIAL / FINAL / PROCESS

    // RAW MATERIAL → Send to Finance
    if ("RAW_MATERIAL".equalsIgnoreCase(inspectionType)) {

        TransitionMaster paymentVerifyTransition =
                transitionMasterRepository.findByTransitionName("PAYMENT_VERIFICATION");

        WorkflowTransition financeStep = createNextTransition(
                verified,
                paymentVerifyTransition,
                "PAYMENT_VERIFICATION",
                "Awaiting Finance Verification",
                req
        );

      //  financeStep.setAssignedToUser(getFinanceUserForRio(current.getCreatedBy()));
        financeStep.setAssignedToUser(1);
        workflowTransitionRepository.save(financeStep);

        return mapWorkflowTransition(financeStep);
    }

    // FINAL / PROCESS → direct CALL_REGISTERED
    TransitionMaster callRegTransition =
            transitionMasterRepository.findByTransitionName("CALL_REGISTERED");

    WorkflowTransition callReg = createNextTransition(
            verified,
            callRegTransition,
            "CALL_REGISTERED",
            "Call Registered",
            req
    );

    if ("PROCESS".equalsIgnoreCase(inspectionType)) {

        // First time PROCESS IE assignment → use the Process IE user ID
        Integer processIeUserId = getProcessIeUserFromCluster(req.getPincode());

        callReg.setAssignedToUser(processIeUserId);
        callReg.setProcessIeUserId(processIeUserId);

    } else {

        InspectionCall ic = inspectionCallRepository.findByIcNumber(req.getRequestId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_INVALID,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Place of Inspection (POI) is not assigned"
                        )
                ));

      //  PincodePoIMapping poi = pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection());


        PincodePoIMapping poi =
                pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection())
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Invalid POI code"
                                )
                        ));
        String stage = null;
        if(ic.getTypeOfCall().equalsIgnoreCase("Raw Material")){
            stage ="R";
        }


        // RAW / FINAL → normal IE assignment
       // callReg.setAssignedToUser(assignIE(req.getPincode()));

        callReg.setAssignedToUser(assignIE(poi.getPinCode(),"ERC",stage,ic.getPlaceOfInspection() ));
    }


   // callReg.setAssignedToUser(assignIE(req.getPincode()));
    workflowTransitionRepository.save(callReg);

    return mapWorkflowTransition(callReg);
}




    private WorkflowTransitionDto returnToVendor(WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster rejectTransition = transitionMasterRepository
                .findByTransitionName("RETURN_TO_VENDOR");

        WorkflowTransition next = createNextTransition(current, rejectTransition, "RETURNED",
                req.getRemarks(), req);
        WorkflowTransition vendorCreated =
                workflowTransitionRepository.findByStatusRequestIdAndCurrentRoleName("Created", req.getRequestId(), "Vendor");
        next.setAssignedToUser(vendorCreated.getCreatedBy());

        return mapWorkflowTransition(next);
    }

    private WorkflowTransitionDto fixRouting(WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster fixRoutingTransition = transitionMasterRepository
                .findByTransitionName("FIX_ROUTING");

        WorkflowTransition last =
                workflowTransitionRepository.findTopByRequestIdOrderByWorkflowTransitionIdDesc(req.getRequestId());


        WorkflowTransition next = createNextTransition(current, fixRoutingTransition, "ROUTED_CORRECTION",
                "Routing corrected & forwarded to correct RIO", req);
        next.setAssignedToUser(req.getAssignUserId());
        next.setRio(req.getRioRouteChange());
        next.setWorkflowSequence(last.getWorkflowSequence()+1);
        return mapWorkflowTransition(next);
    }

    private WorkflowTransitionDto vendorResubmit(WorkflowTransition current, TransitionActionReqDto req) {

        WorkflowTransition last = workflowTransitionRepository
                .findTopByRequestIdOrderByWorkflowTransitionIdDesc(current.getRequestId());

        if (last.getAssignedToUser() == null ||
                !last.getAssignedToUser().equals(req.getActionBy())) {// Only assigned IE can act

            throw new InvalidInputException(
                    new ErrorDetails(
                            AppConstant.ACCESS_DENIED,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "You are not authorized to act on this inspection call."
                    )
            );
        }
        TransitionMaster transition = transitionMasterRepository
                .findByTransitionName(req.getAction());

        if (transition == null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "transition not defined"));
        }

        WorkflowTransition next = new WorkflowTransition();
        next.setWorkflowId(current.getWorkflowId());
        next.setTransitionId(transition.getTransitionId());
        next.setRequestId(current.getRequestId());
        next.setStatus(AppConstant.Vendor_ReSubmitted);
        next.setAction(AppConstant.APPROVE_TYPE);
        next.setRemarks(req.getRemarks() == null ? "Resubmitted after correction" : req.getRemarks());
        //next.setCreatedBy(req.getActionBy());
        next.setCreatedBy(current.getCreatedBy());
        next.setModifiedBy(req.getActionBy());
        next.setCreatedDate(new Date());
        //  next.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
        //  next.setNextRole(String.valueOf(transition.getNextRoleId()));
        next.setCurrentRoleName(roleNameById(transition.getCurrentRoleId()));
        next.setNextRole(String.valueOf(transition.getNextRoleId()));
        next.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
        next.setNextRoleName(roleNameById(transition.getNextRoleId()));

        next.setJobStatus("IN_PROGRESS");
       /* PincodeCluster cluster = pincodeClusterRepository.findByPincode(req.getPincode())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "No cluster found for pincode")));

        // Step 2: Get region for that cluster
        RegionCluster region = regionClusterRepository.findByClusterName(cluster.getClusterName())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "No region found for cluster")
                ));

        // Step 3: Find RIO user for that region
        ClusterRioUser rioUser = clusterRioUserRepository.findByClusterName(cluster.getClusterName())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "No RIO user found for region")
                ));

        Integer assignedRioUserId = rioUser.getRioUserId();

        next.setAssignedToUser(assignedRioUserId);*/
        InspectionCall ic = inspectionCallRepository.findByIcNumber(req.getRequestId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_INVALID,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Place of Inspection (POI) is not assigned"
                        )
                ));

        //  PincodePoIMapping poi = pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection());


        PincodePoIMapping poi =
                pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection())
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Invalid POI code"
                                )
                        ));
        String stage = null;
        if(ic.getTypeOfCall().equalsIgnoreCase("Raw Material")){
            stage ="R";
        }
        String product ="ERC";
        String pinCode = poi.getPinCode();

        IEFieldsMapping mapping =
                ieFieldsMappingRepository
                        .findByPinCodeProductAndStageMatch(pinCode, product, stage)
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "No IE mapping for given pin/product/stage")));

         String  rio =mapping.getRio();

         next.setRio(rio);

        next.setWorkflowSequence(last.getWorkflowSequence()+1);

        workflowTransitionRepository.save(next);

        return mapWorkflowTransition(next);
    }

    private TransitionMaster resolveConditionalTransition(
            WorkflowTransition current,
            TransitionActionReqDto req
    ) {

        TransitionMaster previous =
                transitionMasterRepository.findById(current.getTransitionId())
                        .orElseThrow(() ->
                                new BusinessException(new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Previous transition not found"
                                ))
                        );


        System.out.println("DEBUG prevTransitionId=" + previous.getTransitionId()
                + ", name=" + previous.getTransitionName()
                + ", currRole=" + previous.getCurrentRoleId()
                + ", nextRole=" + previous.getNextRoleId()
                + ", uiAction=" + req.getAction());

        if (Objects.equals(previous.getCurrentRoleId(), previous.getNextRoleId())) {


            // We only use CURRENT_ACTION = UI action, for the SAME role.

            List<TransitionMaster> actionTransitions =
                    transitionMasterRepository.findByWorkflowId(current.getWorkflowId())
                            .stream()
                            // same actor role
                            .filter(t -> Objects.equals(t.getCurrentRoleId(), previous.getNextRoleId()))
                            // CURRENT_ACTION must match the action user performed
                            .filter(t -> t.getCurrentAction() != null &&
                                    t.getCurrentAction().equalsIgnoreCase(req.getAction()))
                            .toList();

            if (actionTransitions.isEmpty()) {
                throw new BusinessException(new ErrorDetails(
                        AppConstant.INVALID_WORKFLOW_TRANSITION,
                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "No transition found for CURRENT_ACTION: " + req.getAction()
                ));
            }

            // Normally only one row will match → use first
            return actionTransitions.get(0);
        }

        Integer currentRoleId = previous.getNextRoleId();

        List<TransitionMaster> transitions =
                transitionMasterRepository
                        .findByWorkflowId(current.getWorkflowId())
                        .stream()
                        .filter(t -> Objects.equals(t.getCurrentRoleId(), currentRoleId))
                        .toList();

        for (TransitionMaster t : transitions) {

            if (t.getConditionId() != null) {
                TransitionConditionMaster cond =
                        transitionConditionMasterRepository.findById(t.getConditionId()).orElse(null);

                if (cond != null && validateCondition(cond, req)) {
                    return t;
                }
            }
        }

        // No match found
        throw new BusinessException(new ErrorDetails(
                AppConstant.INVALID_WORKFLOW_TRANSITION,
                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                AppConstant.ERROR_TYPE_VALIDATION,
                "No matching transition found for action " + req.getAction()
        ));
    }




    private boolean validateCondition(TransitionConditionMaster cond, TransitionActionReqDto req) {

        switch (cond.getConditionKey()) {

            case "PO_STATUS":
                return req.getPoStatus() != null &&
                        req.getPoStatus().equalsIgnoreCase(cond.getConditionValue());

            case "CM_DECISION":
                return req.getCmDecision() != null &&
                        req.getCmDecision().equalsIgnoreCase(cond.getConditionValue());
            case "SBU_HEAD_DECISION":
                return req.getSbuHeadDecision() != null &&
                        req.getSbuHeadDecision().equalsIgnoreCase(cond.getConditionValue());

            case "PAYMENT_TYPE":
                return req.getPaymentType() != null &&
                        req.getPaymentType().equalsIgnoreCase(cond.getConditionValue());

            case "MATERIAL_AVAILABLE":
                return req.getMaterialAvailable() != null &&
                        req.getMaterialAvailable().equalsIgnoreCase(cond.getConditionValue());

            case "RESULT_STATUS":
                return req.getResultStatus() != null &&
                        req.getResultStatus().equalsIgnoreCase(cond.getConditionValue());

            case "CM_FINAL_APPROVAL":
                return req.getCmFinalApproval() != null &&
                        req.getCmFinalApproval().equalsIgnoreCase(cond.getConditionValue());
        }
        return false;
    }


    private WorkflowTransition createNextTransition(
            WorkflowTransition current,
            TransitionMaster transition,
            String status,
            String remarks,
            TransitionActionReqDto req
    ) {
        WorkflowTransition last =
                workflowTransitionRepository.findTopByRequestIdOrderByWorkflowTransitionIdDesc(req.getRequestId());
System.out.print(last);
        WorkflowTransition next = new WorkflowTransition();
        next.setWorkflowId(current.getWorkflowId());
        next.setTransitionId(transition.getTransitionId());
       // next.setTransitionOrder(transition.getTransitionOrder());
        next.setRequestId(current.getRequestId());
        next.setStatus(status);
        next.setAction(req.getAction());
        next.setRemarks(remarks);
        next.setCreatedDate(new Date());
     //   next.setCreatedBy(req.getActionBy());
        next.setCreatedBy(current.getCreatedBy());
        next.setModifiedBy(req.getActionBy());
      //  String inspectionType = "PROCESS";
        String inspectionType ="Raw Material";
        //PROCESS IE ROLE OVERRIDE
        if (inspectionType != null
                && inspectionType.equalsIgnoreCase("PROCESS")) {

            Integer processIeRoleId = 7; //  ROLE ID FOR PROCESS IE

            // If current role is IE (3), override to Process IE
            if (transition.getCurrentRoleId()!= null && transition.getCurrentRoleId() == 3) {
                next.setCurrentRole(String.valueOf(processIeRoleId));
                next.setCurrentRoleName(roleNameById(processIeRoleId));
            }else{
                next.setCurrentRoleName(roleNameById(transition.getCurrentRoleId()));
                next.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
            }
            // If next role is IE (3), override to Process IE
            if (transition.getNextRoleId() != null && transition.getNextRoleId() == 3) {
                next.setNextRole(String.valueOf(processIeRoleId));
                next.setNextRoleName(roleNameById(processIeRoleId));
            }else{
                next.setNextRole(String.valueOf(transition.getNextRoleId()));
                next.setNextRoleName(roleNameById(transition.getNextRoleId()));
            }
        }else{
            next.setCurrentRoleName(roleNameById(transition.getCurrentRoleId()));
            next.setNextRole(String.valueOf(transition.getNextRoleId()));
            next.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
            next.setNextRoleName(roleNameById(transition.getNextRoleId()));
        }

        next.setAssignedToUser(current.getAssignedToUser());

        if(inspectionType.equalsIgnoreCase("PROCESS")){
            next.setProcessIeUserId(current.getProcessIeUserId());
        }
        next.setJobStatus(determineJobStatus(req.getAction()));
        next.setWorkflowSequence(last.getWorkflowSequence()+1);
        workflowTransitionRepository.save(next);

        return next;
    }
/*
    private Integer assignIE(String pincode) {

        //  Get Cluster
        PincodeCluster cluster = pincodeClusterRepository.findByPincode(pincode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Cluster not found for pincode")));

        String clusterName = cluster.getClusterName();

        // Always assign primary IE if available (no workload check)
        Integer primaryIe = getPrimaryIeForCluster(clusterName);
        if (primaryIe != null) {
            return primaryIe;
        }

        //  If no primary IE → choose best secondary IE
        Integer secondaryIe = getSecondaryIeForCluster(clusterName);
        if (secondaryIe != null) {
            return secondaryIe;
        }

        throw new BusinessException(
                new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "No IE available for assignment")
        );
    }*/
private Integer assignIE(
        String pinCode,
        String product,
        String stage,
        String poiCode) {

    // 1. Validate Pin + Product + Stage → RIO
//    IEFieldsMapping mapping =
//            ieFieldsMappingRepository
//                    .findByPinCodeAndProductAndStage(pinCode, product, stage)
//                    .orElseThrow(() -> new BusinessException(
//                            new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
//                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
//                                    AppConstant.ERROR_TYPE_VALIDATION,
//                                    "No IE mapping for given pin/product/stage")));

    System.out.print(pinCode+""+product +""+ stage +""+poiCode);

    IEFieldsMapping mapping =
            ieFieldsMappingRepository
                    .findByPinCodeProductAndStageMatch(pinCode, product, stage)
                    .orElseThrow(() -> new BusinessException(
                            new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                    AppConstant.ERROR_TYPE_VALIDATION,
                                    "No IE mapping for given pin/product/stage")));


    String rio = mapping.getRio();
     // Validate POI
    boolean poiValid = pincodePoIMappingRepository
            .existsByPinCodeAndPoiCode(pinCode, poiCode);

    if (!poiValid) {
        throw new BusinessException(
                new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION,
                        "Invalid POI for pin code")
        );
    }

    // 3. Try PRIMARY IE
//    Optional<Integer> primaryIe =
//            iePincodePoiMappingRepository
//                    .findPrimaryIe(pinCode, product, poiCode);
//
//    if (primaryIe.isPresent()) {
//        return primaryIe.get();
//    }
//
//    // 4. Try SECONDARY IE
//    Optional<Integer> secondaryIe =
//            iePincodePoiMappingRepository
//                    .findSecondaryIe(pinCode, product, poiCode, rio);
//
//    if (secondaryIe.isPresent()) {
//        return secondaryIe.get();
//    }
    Optional<String> primaryIe =  iePincodePoiMappingRepository.findPrimaryIe(pinCode, product, poiCode);
    if (primaryIe.isPresent()) {
        UserMaster um = userMasterRepository.findByEmployeeCode(primaryIe.get());
       return um.getUserId();
    }
    Optional<String> secondaryIe = iePincodePoiMappingRepository.findSecondaryIe(pinCode, product, poiCode);

    if (secondaryIe.isPresent()) {
        UserMaster um = userMasterRepository.findByEmployeeCode(secondaryIe.get());
        return um.getUserId();
    }

    // 5. No IE
    throw new BusinessException(
            new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                    AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION,
                    "No IE available for assignment")
    );
}


    private int getIeWorkload(Integer ieUserId) {
        return workflowTransitionRepository.countActiveCallsForIE(ieUserId);
    }

    private Integer getPrimaryIeForCluster(String clusterName) {

        ClusterPrimaryIe primary = clusterPrimaryIeRepository
                .findByClusterName(clusterName)
                .orElse(null);

        return primary != null ? primary.getIeUserId() : null;
    }

    private Integer getSecondaryIeForCluster(String clusterName) {

        List<ClusterSecondaryIe> list =
                clusterSecondaryIeRepository.findByClusterNameOrderByPriorityOrderAsc(clusterName);

        if (list.isEmpty()) return null;

        Integer bestIe = null;
        int minWorkload = Integer.MAX_VALUE;

        for (ClusterSecondaryIe ie : list) {
            int workload = getIeWorkload(ie.getIeUserId());
            if (workload < minWorkload) {
                minWorkload = workload;
                bestIe = ie.getIeUserId();
            }
        }

        return bestIe;
    }




    private WorkflowTransitionDto cmReturnToIe(WorkflowTransition current, TransitionActionReqDto req) {

       /* if (req.getNextIeUserId() == null) {
            throw new InvalidInputException(
                    new ErrorDetails(
                            AppConstant.INVALID_WORKFLOW_TRANSITION,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "IE user ID required when CM returns IC"
                    )
            );
        }*/

        WorkflowTransition last =
                        workflowTransitionRepository.findTopByRequestIdOrderByWorkflowTransitionIdDesc(req.getRequestId());

                TransitionMaster transition = transitionMasterRepository
                .findByTransitionName("CM_RETURN_TO_IE");

        WorkflowTransition next = new WorkflowTransition();
        next.setWorkflowId(current.getWorkflowId());
        next.setTransitionId(transition.getTransitionId());
        next.setRequestId(current.getRequestId());
        next.setStatus("RETURNED_BY_CM");
        next.setAction("CM_RETURN_TO_IE");
        next.setRemarks(req.getRemarks());
        //next.setCreatedBy(req.getActionBy());
        next.setCreatedBy(current.getCreatedBy());
        next.setModifiedBy(req.getActionBy());
        next.setCreatedDate(new Date());
      //  next.setCurrentRoleName("CM");
      //  next.setNextRoleName("IE");
      //  next.setCurrentRole("4"); // CM
      //  next.setNextRole("3");    // IE
        next.setCurrentRoleName(roleNameById(transition.getCurrentRoleId()));
        next.setNextRole(String.valueOf(transition.getNextRoleId()));
        next.setCurrentRole(String.valueOf(transition.getCurrentRoleId()));
        next.setNextRoleName(roleNameById(transition.getNextRoleId()));
        next.setAssignedToUser(req.getAssignUserId()); // <-- assigns to secondary selected  by cm

        next.setJobStatus("ASSIGNED");
        next.setWorkflowSequence(last.getWorkflowSequence());

        workflowTransitionRepository.save(next);

        return mapWorkflowTransition(next);
    }


    private String roleNameById(Integer roleId) {
        if (Objects.nonNull(roleId)) {
            return roleMasterRepository.findById(roleId).orElse(new RoleMaster()).getRoleName();
        } else {
            return null;
        }
    }

    private WorkflowTransitionDto mapWorkflowTransition(WorkflowTransition wt) {

        Optional<InspectionCall> ic = inspectionCallRepository.findByIcNumber(wt.getRequestId());

        InspectionCall i =null;
        if(ic.isPresent()){
            i = ic.get();
        }
        WorkflowTransitionDto dto = new WorkflowTransitionDto();
        dto.setWorkflowTransitionId(wt.getWorkflowTransitionId());
        dto.setWorkflowId(wt.getWorkflowId());
        dto.setTransitionId(wt.getTransitionId());
        dto.setRequestId(wt.getRequestId());
        dto.setStatus(wt.getStatus());
        dto.setAction(wt.getAction());
        //dto.setAction(wt.getStatus());
        dto.setRemarks(wt.getRemarks());
        dto.setCreatedBy(wt.getCreatedBy());
        dto.setCreatedDate(wt.getCreatedDate());
        dto.setCurrentRole(wt.getCurrentRole());
        dto.setNextRole(wt.getNextRole());
        dto.setAssignedToUser(wt.getAssignedToUser());
        dto.setWorkflowSequence(wt.getWorkflowSequence());
        dto.setModifiedBy(wt.getModifiedBy());
        dto.setRio(wt.getRio());
        if(ic.isPresent()){
            dto.setPoNo(i.getPoNo());
            dto.setVendorName(i.getVendorId());
            dto.setProductType(i.getTypeOfCall());
            dto.setDesiredInspectionDate(String.valueOf(i.getDesiredInspectionDate()));
        }
      //  dto.setTransitionOrder(wt.getTransitionOrder());
        return dto;
    }

/*
    private Integer getCmUserFromIeUser(Integer ieUserId) {

        // Step 1: Find cluster of IE (check primary)
        ClusterPrimaryIe primaryIe = clusterPrimaryIeRepository
                .findByIeUserId(ieUserId)
                .orElse(null);

        String clusterName = null;

        if (primaryIe != null) {
            clusterName = primaryIe.getClusterName();
        } else {
            // Check secondary IE list
            ClusterSecondaryIe secondaryIe = clusterSecondaryIeRepository
                    .findByIeUserId(ieUserId)
                    .orElse(null);

            if (secondaryIe != null) {
                clusterName = secondaryIe.getClusterName();
            }
        }

        if (clusterName == null) return null;

        // Find CM for that cluster
        ClusterCmUser cmUser = clusterCmUserRepository
                .findByClusterName(clusterName)
                .orElse(null);

        return cmUser != null ? cmUser.getCmUserId() : null;
    }*/

    private Integer getCmUserFromIeEmployeeCode(String ieEmployeeCode) {

        return ieControllingManagerRepository
                .findByIeEmployeeCode(ieEmployeeCode)
                .map(IeControllingManager::getCmUserId)
                .orElse(null);
    }


    private String determineJobStatus(String action) {

        if (action == null) return "UNKNOWN";

        switch (action.toUpperCase()) {

            case "VERIFY":
            case "VERIFY_PO_DETAILS":
                return "VERIFIED";

            case "CM_RETURN_TO_IE":
                return "IN_PROGRESS";

            case "ENTER_SHIFT_DETAILS_AND_START_INSPECTION":
            case "VERIFY_MATERIAL_AVAILABILITY":
            case "ENTRY_INSPECTION_RESULTS":
                return "IN_PROGRESS";

            case "REQUEST_CORRECTION_TO_CM":
                return "APPROVED";

            case "PAUSE_INSPECTION_RESUME_NEXT_DAY":
                return "PAUSED";
            case "BLOCK_DUE_TO_PAYMENT":
                return "BLOCKED";

            case "COMPLETE_INSPECTION":
            case "INSPECTION_COMPLETE_CONFIRM":
            case "SUBMIT_INSPECTION_RESULTS":
                return "COMPLETED";
            case "CM_REJECT_CORRECTION":
                return  "REJECTED";

            case "VENDOR_CANCEL":
            case "CANCEL_DUE_TO_NO_MATERIAL":
                return "CANCELLED";

            default:
                return "IN_PROGRESS";
        }
    }
/*
    private TransitionMaster handlePaymentCheck(TransitionActionReqDto req, List<TransitionMaster> transitions) {

        //here we have to fetch data from payement master ic is payable or non payable
        String paymentType = "PAYABLE";     // PAYABLE / NON_PAYABLE
        Boolean paymentCompleted = false; // TRUE / FALSE

        TransitionMaster cancelled = transitions.stream()
                .filter(t -> t.getTransitionName().equalsIgnoreCase("CALL_CANCELLED_NON_PAYABLE"))
                .findFirst().orElse(null);

        TransitionMaster blocked = transitions.stream()
                .filter(t -> t.getTransitionName().equalsIgnoreCase("CALL_BLOCKED_PENDING_PAYMENT"))
                .findFirst().orElse(null);

        if (paymentType == null) return null;

        // NON PAYABLE → cancel
        if (paymentType.equalsIgnoreCase("NON_PAYABLE")) {
            return cancelled;
        }

        // PAYABLE CASE
        if (paymentType.equalsIgnoreCase("PAYABLE")) {

            if (paymentCompleted != null && !paymentCompleted) {
                return blocked;   // Payable but payment not done → BLOCK
            }

            // Payable & Payment done → Cancel
            return cancelled;
        }

        return null;
    }*/

    private TransitionMaster handlePaymentCheck(TransitionActionReqDto req, List<TransitionMaster> transitions) {

        /*   // 1. Fetch IC payment details using requestId
        ICPaymentMaster payment = icPaymentMasterRepository
                .findByRequestId(req.getRequestId())
                .orElse(null);

        if (payment == null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Payment details not found for requestId: " + req.getRequestId())
            );
        }*/
        //here we have to fetch data from payement master ic is payable or non payable
        String paymentType = "NON_PAYABLE";     // PAYABLE / NON_PAYABLE
        Boolean paymentCompleted = false; // TRUE / FALSE

        System.out.print(paymentCompleted);
        System.out.print(paymentType);
       // String paymentType = payment.getPaymentType(); // PAYABLE / NON_PAYABLE
      //  Boolean paymentCompleted = payment.getPaymentCompleted(); // true / false

        // 2. Find the transitions
        TransitionMaster cancelled = transitions.stream()
                .filter(t -> t.getTransitionName().equalsIgnoreCase("CALL_CANCELLED_NON_PAYABLE"))
                .findFirst().orElse(null);

        System.out.print("cancelled" +cancelled);
        TransitionMaster blocked = transitions.stream()
                .filter(t -> t.getTransitionName().equalsIgnoreCase("CALL_BLOCKED_PENDING_PAYMENT"))
                .findFirst().orElse(null);
        System.out.print("blocked" +blocked);
        // 3. Apply logic

        // NON PAYABLE → Cancel directly
        if ("NON_PAYABLE".equalsIgnoreCase(paymentType)) {
            return cancelled;
        }

        // PAYABLE CASE
        if ("PAYABLE".equalsIgnoreCase(paymentType)) {

            // If payment NOT completed → BLOCK
            if (paymentCompleted != null && !paymentCompleted) {
                return blocked;
            }

            // If payment completed → CANCEL (CM rejected even after payment)
            return cancelled;
        }

        return null;
    }

    private WorkflowTransitionDto handleFinancePaymentVerification(
            WorkflowTransition current, TransitionActionReqDto req) {

        boolean paymentReceived =
                req.getPaymentReceivedStatus() != null &&
                        req.getPaymentReceivedStatus().equalsIgnoreCase("YES");

        if (!paymentReceived) {
            // Payment not received → PARKED
            TransitionMaster parked =
                    transitionMasterRepository.findByTransitionName("PARKED_PAYMENT_NOT_RECEIVED");

            WorkflowTransition next = createNextTransition(
                    current, parked, "PARKED", "Payment Not Received", req
            );

            next.setAssignedToUser(current.getAssignedToUser()); // Finance keeps it
            workflowTransitionRepository.save(next);

            return mapWorkflowTransition(next);
        }

        // PAYMENT RECEIVED → CALL REGISTERED
        TransitionMaster callReg =
                transitionMasterRepository.findByTransitionName("CALL_REGISTERED");

        WorkflowTransition registered = createNextTransition(
                current, callReg, "CALL_REGISTERED", "Payment Verified - Call Registered", req
        );

      //  registered.setAssignedToUser(assignIE(req.getPincode()));
       InspectionCall ic = inspectionCallRepository.findByIcNumber(req.getRequestId())
               .orElseThrow(() -> new BusinessException(
                       new ErrorDetails(
                               AppConstant.ERROR_CODE_INVALID,
                               AppConstant.ERROR_TYPE_CODE_VALIDATION,
                               AppConstant.ERROR_TYPE_VALIDATION,
                               "Place of Inspection (POI) is not assigned"
                       )
               ));

        System.out.println("Inspection call"+ ic.getPlaceOfInspection());
       // PincodePoIMapping poi = pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection());


        PincodePoIMapping poi =
                pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection())
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Invalid POI code"
                                )
                        ));

        System.out.println("poi"+ poi);
        String stage = null;
        if(ic.getTypeOfCall().equalsIgnoreCase("Raw Material")){
            stage ="R";
        }
        // RAW / FINAL → normal IE assignment
        // callReg.setAssignedToUser(assignIE(req.getPincode()));

        registered.setAssignedToUser(assignIE(poi.getPinCode(),"ERC",stage,ic.getPlaceOfInspection() ));

        registered.setJobStatus("REGISTERED");

        workflowTransitionRepository.save(registered);
        return mapWorkflowTransition(registered);
    }

    private WorkflowTransitionDto handlePaymentReceived(
            WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster callReg =
                transitionMasterRepository.findByTransitionName("CALL_REGISTERED");

        WorkflowTransition next = createNextTransition(
                current, callReg, "CALL_REGISTERED",
                "Payment Received - Call Registered", req
        );

      //  next.setAssignedToUser(assignIE(req.getPincode()));
        InspectionCall ic = inspectionCallRepository.findByIcNumber(req.getRequestId())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(
                                AppConstant.ERROR_CODE_INVALID,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Place of Inspection (POI) is not assigned"
                        )
                ));

       // PincodePoIMapping poi = pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection());

        PincodePoIMapping poi =
                pincodePoIMappingRepository.findByPoiCode(ic.getPlaceOfInspection())
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(
                                        AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Invalid POI code"
                                )
                        ));
        String stage = null;
        if(ic.getTypeOfCall().equalsIgnoreCase("Raw Material")){
            stage ="R";
        }

        // RAW / FINAL → normal IE assignment
        // callReg.setAssignedToUser(assignIE(req.getPincode()));

      next.setAssignedToUser(assignIE(poi.getPinCode(),"ERC",stage,ic.getPlaceOfInspection() ));

        next.setJobStatus("REGISTERED");

        workflowTransitionRepository.save(next);
        return mapWorkflowTransition(next);
    }
    private WorkflowTransitionDto handlePaymentNotReceived(
            WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster parked =
                transitionMasterRepository.findByTransitionName("PARKED_PAYMENT_NOT_RECEIVED");

        WorkflowTransition next = createNextTransition(
                current, parked, "PARKED", "Payment Not Received", req
        );

        next.setAssignedToUser(current.getAssignedToUser()); // Finance retains
        workflowTransitionRepository.save(next);

        return mapWorkflowTransition(next);
    }

    @Override
    public List<WorkflowTransitionDto> allPendingWorkflowTransition(String roleName) {

        List<WorkflowTransition> pending =
                workflowTransitionRepository.findPendingByRole(roleName);

        return pending.stream()
                .sorted(Comparator.comparing(WorkflowTransition::getRequestId)
                        .thenComparing(WorkflowTransition::getCreatedDate))
                .map(this::mapWorkflowTransition)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkflowTransitionDto> allPendingQtyEditTransitions(String roleName) {

        List<WorkflowTransition> pendingQty =
                workflowTransitionRepository.findPendingQtyEditByRole(roleName);

        return pendingQty.stream()
                .sorted(Comparator.comparing(WorkflowTransition::getRequestId)
                        .thenComparing(WorkflowTransition::getCreatedDate))
                .map(this::mapWorkflowTransition)
                .collect(Collectors.toList());
    }




    private Integer getProcessIeUserFromCluster(String pincode) {

        // 1. Find cluster
        PincodeCluster cluster = pincodeClusterRepository.findByPincode(pincode)
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Cluster not found for pincode: " + pincode)
                ));

        // 2. Find Process IE mapped to that cluster
        ProcessIeMaster process = processIeMasterRepository
                .findByClusterName(cluster.getClusterName())
                .orElseThrow(() -> new BusinessException(
                        new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_CODE_RESOURCE,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "No Process IE found for cluster: " + cluster.getClusterName())
                ));

        return process.getProcessIeUserId();
    }


    @Override
    public List<WorkflowTransitionDto> workflowTransitionHistory(String requestId) {

        List<WorkflowTransitionDto> workflowTransitionDtoList = new ArrayList<>();
        List<WorkflowTransition> workflowTransitionList = null;
        workflowTransitionList = workflowTransitionRepository.findByRequestId(requestId);
        if (Objects.nonNull(workflowTransitionList) && !workflowTransitionList.isEmpty()) {
            workflowTransitionDtoList = workflowTransitionList.stream().sorted(Comparator.comparing(WorkflowTransition::getWorkflowSequence).reversed()).map(e -> {
                return mapWorkflowTransition(e);
            }).collect(Collectors.toList());
        }

        return workflowTransitionDtoList;
    }


    private WorkflowTransitionDto requestQtyEdit(WorkflowTransition current, TransitionActionReqDto req) {


        // Vendor can request only before inspection initiation
        WorkflowTransition initiated = workflowTransitionRepository
                .findTopByRequestIdAndStatus(current.getRequestId(), "INITIATE_INSPECTION");

        if (initiated != null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Inspection already initiated. Quantity edit not allowed."));
        }

        // Must be after CALL_REGISTERED
        WorkflowTransition callReg = workflowTransitionRepository
                .findTopByRequestIdAndStatus(current.getRequestId(), "CALL_REGISTERED");

        if (callReg == null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "Call must be registered before requesting qty edit."));
        }

        // Cutoff date logic
       WorkflowTransition schedule = workflowTransitionRepository
                .findTopByRequestIdAndStatus(current.getRequestId(), "IE_SCHEDULED");

       // Date inspectionDate = 08-12-2025;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

// Parse LocalDate
        LocalDate inspectionLocalDate = LocalDate.parse("10-12-2025", formatter);

// Convert LocalDate → Date
        Date inspectionDate = Date.from(inspectionLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (schedule != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(inspectionDate);
            cal.add(Calendar.DAY_OF_MONTH, -1);  // 1 day before inspection

            if (new Date().after(cal.getTime())) {
                throw new BusinessException(
                        new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION,
                                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                AppConstant.ERROR_TYPE_VALIDATION,
                                "Quantity edit request not allowed after schedule cutoff date.")
                );
            }
        }


        // Forward to CM
        TransitionMaster t = transitionMasterRepository
                .findByTransitionName("REQUEST_QTY_EDIT");

        WorkflowTransition next = createNextTransition(
                current,
                t,
                "QTY_EDIT_REQUESTED",
                req.getRemarks(),
                req
        );

        // Assign CM of this IE's cluster
       // Integer cmUserId = getCmUserFromIeUser(callReg.getAssignedToUser());
        Optional<UserMaster> um = userMasterRepository.findByUserId(callReg.getAssignedToUser());

        Integer cmUserId =null;
        if(um.isPresent()){
            UserMaster u = um.get();
           cmUserId =  getCmUserFromIeEmployeeCode(u.getEmployeeCode());
        }


        next.setAssignedToUser(cmUserId);

        workflowTransitionRepository.save(next);
        return mapWorkflowTransition(next);
    }


    private WorkflowTransitionDto cmQtyDecision(WorkflowTransition current, TransitionActionReqDto req) {

        TransitionMaster nextT = resolveConditionalTransition(current, req);

        if (nextT == null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "No valid qty edit decision path."));
        }

        WorkflowTransition initiated = workflowTransitionRepository
                .findTopByRequestIdAndStatus(current.getRequestId(), "INITIATE_INSPECTION");

        if (initiated != null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.INVALID_WORKFLOW_TRANSITION,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,"Inspection initiated. CM cannot approve or reject now."));
        }


        WorkflowTransition next = createNextTransition(
                current,
                nextT,
                req.getCmDecision().equalsIgnoreCase("APPROVED") ?
                        "QTY_APPROVED" :
                        "QTY_REJECTED",
                req.getRemarks(),
                req
        );

        // Return back to vendor
        next.setAssignedToUser(current.getCreatedBy());

        workflowTransitionRepository.save(next);
        return mapWorkflowTransition(next);
    }


    @Override
    public List<WorkflowTransitionDto> allBlockedWorkflowTransitions() {

        List<WorkflowTransition> blocked =
                workflowTransitionRepository.findBlockedTransitions();

        return blocked.stream()
                .sorted(Comparator.comparing(WorkflowTransition::getRequestId)
                        .thenComparing(WorkflowTransition::getCreatedDate))
                .map(this::mapWorkflowTransition)
                .collect(Collectors.toList());
    }


    @Override
    public WorkflowDto workflowByWorkflowName(String workflowName) {
        WorkflowDto workflowDto = null;

        if (Objects.nonNull(workflowName)) {
            WorkflowMaster workflowMaster = workflowMasterRepository.findByWorkflowName(workflowName);
            if (Objects.nonNull(workflowMaster)) {
                workflowDto = new WorkflowDto();
                workflowDto.setWorkflowId(workflowMaster.getWorkflowId());
                workflowDto.setWorkflowName(workflowMaster.getWorkflowName());
                workflowDto.setCreatedBy(workflowMaster.getCreatedBy());
                workflowDto.setCreatedDate(workflowMaster.getCreatedDate());
            } else {
                throw new InvalidInputException(new ErrorDetails(AppConstant.WORKFLOW_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                        AppConstant.ERROR_TYPE_VALIDATION, "Workflow not found."));
            }
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.USER_INVALID_INPUT, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Invalid input."));
        }

        return workflowDto;
    }

    @Override
    public List<TransitionDto> transitionsByWorkflowId(Integer workflowId) {

        List<TransitionDto> transitionDtoList = new ArrayList<>();
        List<TransitionMaster> transitionMasterList = transitionMasterRepository.findByWorkflowId(workflowId);

        if (Objects.nonNull(transitionMasterList) && !transitionMasterList.isEmpty()) {
            transitionDtoList = transitionMasterList.stream().map(transitionMaster -> {
                TransitionDto transitionDto = new TransitionDto();
                transitionDto.setWorkflowId(transitionMaster.getWorkflowId());
                transitionDto.setTransitionId(transitionMaster.getTransitionId());
                transitionDto.setCreatedDate(transitionMaster.getCreatedDate());
                transitionDto.setTransitionOrder(transitionMaster.getTransitionOrder());
                transitionDto.setConditionId(transitionMaster.getConditionId());
                transitionDto.setCurrentRoleId(transitionMaster.getCurrentRoleId());
                transitionDto.setNextRoleId(transitionMaster.getNextRoleId());
              
                transitionDto.setTransitionName(transitionMaster.getTransitionName());
                transitionDto.setWorkflowName(workflowNameById(transitionMaster.getWorkflowId()));
                transitionDto.setCurrentRoleName(roleNameById(transitionMaster.getCurrentRoleId()));
                transitionDto.setNextRoleName(roleNameById(transitionMaster.getNextRoleId()));

                return transitionDto;
            }).collect(Collectors.toList());
        } else {
            throw new InvalidInputException(new ErrorDetails(AppConstant.WORKFLOW_NOT_FOUND, AppConstant.ERROR_TYPE_CODE_VALIDATION,
                    AppConstant.ERROR_TYPE_VALIDATION, "Workflow not found."));
        }
        return transitionDtoList;
    }



    private String workflowNameById(Integer workflowId) {
        if (Objects.nonNull(workflowId)) {
            return workflowMasterRepository.findById(workflowId).orElse(new WorkflowMaster()).getWorkflowName();
        } else {
            return null;
        }
    }

    private void assignRescheduleUser(WorkflowTransition next, WorkflowTransition current, TransitionActionReqDto req) {

        String action = req.getAction();

        if (action.equalsIgnoreCase("IE_REQUEST_RESCHEDULE")) {
          //  next.setAssignedToUser(getCmUserFromIeUser(req.getActionBy()));
            Optional<UserMaster> um = userMasterRepository.findByUserId(req.getActionBy());

            Integer cmUserId;
            if(um.isPresent()){
                UserMaster u = um.get();
                cmUserId =  getCmUserFromIeEmployeeCode(u.getEmployeeCode());
            }
        }
        else if (action.equalsIgnoreCase("CM_FORWARD_TO_SBU_HEAD")) {
            next.setAssignedToUser(getSbuHeadUser(req.getPincode()));
        }
        else if (action.contains("APPROVE") || action.contains("REJECT")) {
            next.setAssignedToUser(current.getCreatedBy());
        }
    }
    private Integer getSbuHeadUser(String pincode) {

        // Get the latest workflow transaction (for clusterName)
      Optional<PincodeCluster> cluster = pincodeClusterRepository.findByPincode(pincode);
        if (cluster == null) {
            throw new BusinessException(
                    new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                            AppConstant.ERROR_TYPE_CODE_VALIDATION,
                            AppConstant.ERROR_TYPE_VALIDATION,
                            "No workflow history found for requestId: " + pincode)
            );
        }

        //Get cluster details
        RegionCluster regionCluster =
                regionClusterRepository.findByClusterName(cluster.get().getClusterName())
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "Cluster not found: " + cluster.get().getClusterName())
                        ));

        String regionName = regionCluster.getRegionName();

        // Fetch SBU Head for that region
        RegionSbuHead sbu =
                regionSbuHeadRepository.findByRegionName(regionName)
                        .orElseThrow(() -> new BusinessException(
                                new ErrorDetails(AppConstant.ERROR_CODE_RESOURCE,
                                        AppConstant.ERROR_TYPE_CODE_VALIDATION,
                                        AppConstant.ERROR_TYPE_VALIDATION,
                                        "No SBU Head defined for region: " + regionName)
                        ));

        return sbu.getSbuHeadUserId();
    }


}
