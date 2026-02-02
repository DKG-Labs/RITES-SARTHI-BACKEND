package com.sarthi.dto.reports;

import lombok.Data;

@Data
public class FourthLevelInspectionDto {


        private BasicDetailsDto basicDetails;

        private ProcessQtyDto processQty;

        private ShearingDefectsDto shearingDefects;

        private TurningDefectsDto turningDefects;

        private ForgingDefectsDto forgingDefects;

        private DimensionalDefectsDto dimensionalDefects;

        private VisualDefectsDto visualDefects;

        private TestingDefectsDto testingDefects;

        private FinishingDefectsDto finishingDefects;


}
