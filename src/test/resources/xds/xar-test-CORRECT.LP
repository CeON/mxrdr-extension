
 ***** File heavily cropped for test purposes! *****

 ***** CORRECT ***** (VERSION Mar 15, 2019  BUILT=20190315)  28-Nov-2019

 INPUT PARAMETER VALUES
 ----------------------
 SPACE_GROUP_NUMBER=    1 as used in the INTEGRATE step
 UNIT_CELL_CONSTANTS=    78.061    90.808   116.116  90.055  90.057  90.225 as used by INTEGRATE
 FRIEDEL'S_LAW=FALSE
 PROFILE_FITTING= TRUE 
 OVERLOAD=   1048500     MINPK=  75.00000    WFAC1=   1.0
 INCLUDE_RESOLUTION_RANGE=    50.000     2.100
 NAME_TEMPLATE_OF_DATA_FRAMES=/home/wojtek/synchrotron/ansa-Hamburg-25062019/Ansa/ansa-8-8_w1_5/ansa-p8-8_w1_5_?????.cbf CBF
 DATA_RANGE=       1    1800
 ROTATION_AXIS=  0.999997 -0.000857  0.002056
 OSCILLATION_RANGE=  0.200000
 STARTING_ANGLE=     1.010     STARTING_FRAME=       1
 X-RAY_WAVELENGTH=  1.278650
 INCIDENT_BEAM_DIRECTION= -0.003013 -0.000183  0.782069
 FRACTION_OF_POLARIZATION=   0.980
 POLARIZATION_PLANE_NORMAL=  0.000000  1.000000  0.000000
 AIR=   0.00074
 DETECTOR=PILATUS
 SILICON=  8.162333 SENSOR_THICKNESS=  0.450000
 NUMBER OF DETECTOR SEGMENTS   1
 NX=  2463  NY=  2527    QX=  0.172000  QY=  0.172000
 ORGX=   1221.81  ORGY=   1257.43
 DETECTOR_DISTANCE=   216.250
 DIRECTION_OF_DETECTOR_X-AXIS=   1.00000   0.00000   0.00000
 DIRECTION_OF_DETECTOR_Y-AXIS=   0.00000   1.00000   0.00000
 BEAM_DIVERGENCE_E.S.D.=   0.040
 REFLECTING_RANGE_E.S.D.=   0.212
 MINIMUM_ZETA=     0.050
 MAXIMUM_ERROR_OF_SPOT_POSITION=   3.0
 MAXIMUM_ERROR_OF_SPINDLE_POSITION=   2.0
 MINIMUM_I/SIGMA=   3.0
 REFLECTIONS/CORRECTION_FACTOR=      50
 STRICT_ABSORPTION_CORRECTION=FALSE 
 CORRECTIONS= DECAY MODULATION ABSORPTION                      
 REFERENCE_DATA_SET=                                                  
 FIT_B-FACTOR_TO_REFERENCE_DATA_SET=FALSE 

 1541866 REFLECTIONS ON FILE "INTEGRATE.HKL"
       0 CORRUPTED REFLECTION RECORDS (IGNORED)
       0 MATCHING REFLECTIONS SPECIFIED IN FILE "FILTER.HKL"
       0 REFLECTIONS INCOMPLETE OR OUTSIDE IMAGE RANGE       1 ...    1800
     745 OVERLOADED REFLECTIONS (IGNORED)
  897342 REFLECTIONS OUTSIDE ACCEPTED RESOLUTION RANGES
                OR TOO CLOSE TO ROTATION AXIS (IGNORED)
  643779 REFLECTIONS ACCEPTED



 ******************************************************************************
                      AUTOMATIC SPACE GROUP ASSIGNMENT
 ******************************************************************************

 XDS adopts the following approach.
 (1) it looks for possible symmetries of the crystal lattice
 (2) it computes a redundancy independent R-factor for all enantiomorphous
     point groups compatible with the observed lattice symmetry.
 (3) it selects the group which explains the intensity data at an acceptable,
     redundancy-independent R-factor (Rmeas, Rrim) using a minimum number of
     unique reflections.

 This approach does not test for the presence of screw axes. Consequently,
 orthorhombic cell axes will be specified in increasing length (following
 conventions), despite the possibility that different assignments for the
 cell axes could become necessary for space groups P222(1) and P2(1)2(1)2
 containing one or two screw axes, respectively.

 The user can always override the automatic decisions by specifying the
 correct space group number and unit cell constants in XDS.INP and repeating
 the CORRECT step of XDS. This provides a simple way to rename orthorhombic
 cell constants if screw axes are present.

 In addition, the user has the option to specify in XDS.INP
 (a) a reference data set or
 (b) a reindexing transformation or
 (c) the three basis vectors (if known from processing a previous data set
 taken at the same crystal orientation in a multi-wavelength experiment).
 These features of XDS are useful for resolving the issue of alternative
 settings of polar or rhombohedral cells (like P4, P6, R3).

 =====> Specifications provided by the user in XDS.INP:
 SPACE_GROUP_NUMBER=    0 (0: unknown symmetry and unit cell)
 UNIT_CELL_CONSTANTS=     0.00     0.00     0.00   0.000   0.000   0.000
 UNIT_CELL_A-AXIS=     0.000     0.000     0.000 (0 0 0 : unknown)
 UNIT_CELL_B-AXIS=     0.000     0.000     0.000 (0 0 0 : unknown)
 UNIT_CELL_C-AXIS=     0.000     0.000     0.000 (0 0 0 : unknown)
 REIDX=  0  0  0  0  0  0  0  0  0  0  0  0 (all 0 : not specified)
 TEST_RESOLUTION_RANGE=   10.00    5.00 (Angstrom) for space group determination
 NUMBER OF ACCEPTED REFLECTIONS FOR CALCULATING Rmeas            42375
 NUMBER OF ACCEPTED UNIQUE REFLECTIONS FROM REFERENCE DATA SET       0


 *********** DETERMINATION OF LATTICE CHARACTER AND BRAVAIS LATTICE ***********

 The CHARACTER OF A LATTICE is defined by the metrical parameters of its
 reduced cell as described in the INTERNATIONAL TABLES FOR CRYSTALLOGRAPHY
 Volume A, p. 746 (KLUWER ACADEMIC PUBLISHERS, DORDRECHT/BOSTON/LONDON, 1989).
 Note that more than one lattice character may have the same BRAVAIS LATTICE.

 A lattice character is marked "*" to indicate a lattice consistent with the
 observed locations of the diffraction spots. These marked lattices must have
 low values for the QUALITY OF FIT and their implicated UNIT CELL CONSTANTS
 should not violate the ideal values by more than
 MAX_CELL_AXIS_ERROR=  0.03
 MAX_CELL_ANGLE_ERROR=           2.0 (Degrees)

 The REINDEXING TRANSFORMATION REIDX() consists of 12 integers that relate
 the original indices H,K,L from file INTEGRATE.HKL to the indices H',K',L'
 with respect to the new cell.
    H'=(REIDX(1)*H+REIDX( 2)*K+REIDX( 3)*L)/IDXV+REIDX( 4)
    K'=(REIDX(5)*H+REIDX( 6)*K+REIDX( 7)*L)/IDXV+REIDX( 8)
    L'=(REIDX(9)*H+REIDX(10)*K+REIDX(11)*L)/IDXV+REIDX(12)
 The value of the integer IDXV depends on the lattice type used for
 specifying reflections on file INTEGRATE.HKL; IDXV is
 1 for a primitive,
 2 for a face or body centred,
 3 for a rhombohedral,
 4 for a lattice centred on all faces.
 IDXV is set by XDS and cannot be input by the user.

  LATTICE-  BRAVAIS-   QUALITY  UNIT CELL CONSTANTS (ANGSTROEM & DEGREES)    REINDEXING TRANSFORMATION
 CHARACTER  LATTICE     OF FIT      a      b      c   alpha  beta gamma

 *  44        aP          0.0      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
 *  34        mP          1.6      78.1  116.1   90.8  90.1  90.2  90.1   -1  0  0  0  0  0 -1  0  0 -1  0  0
 *  33        mP          3.1      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
    14        mC        176.7     119.5  120.0  116.1  90.0  90.1  81.4    1  1  0  0 -1  1  0  0  0  0  1  0
    10        mC        179.0     120.0  119.5  116.1  89.9  90.0  98.6   -1  1  0  0 -1 -1  0  0  0  0  1  0

 ****** LATTICE SYMMETRY IMPLICATED BY SPACE GROUP SYMMETRY ******

BRAVAIS-            POSSIBLE SPACE-GROUPS FOR PROTEIN CRYSTALS
  TYPE                     [SPACE GROUP NUMBER,SYMBOL]
  aP      [1,P1]
  mP      [3,P2] [4,P2(1)]
 mC,mI    [5,C2]
  oP      [16,P222] [17,P222(1)] [18,P2(1)2(1)2] [19,P2(1)2(1)2(1)]
  oC      [21,C222] [20,C222(1)]
  oF      [22,F222]
  oI      [23,I222] [24,I2(1)2(1)2(1)]
  tP      [75,P4] [76,P4(1)] [77,P4(2)] [78,P4(3)] [89,P422] [90,P42(1)2]
          [91,P4(1)22] [92,P4(1)2(1)2] [93,P4(2)22] [94,P4(2)2(1)2]
          [95,P4(3)22] [96,P4(3)2(1)2]
  tI      [79,I4] [80,I4(1)] [97,I422] [98,I4(1)22]
  hP      [143,P3] [144,P3(1)] [145,P3(2)] [149,P312] [150,P321] [151,P3(1)12]
          [152,P3(1)21] [153,P3(2)12] [154,P3(2)21] [168,P6] [169,P6(1)]
          [170,P6(5)] [171,P6(2)] [172,P6(4)] [173,P6(3)] [177,P622]
          [178,P6(1)22] [179,P6(5)22] [180,P6(2)22] [181,P6(4)22] [182,P6(3)22]
  hR      [146,R3] [155,R32]
  cP      [195,P23] [198,P2(1)3] [207,P432] [208,P4(2)32] [212,P4(3)32]
          [213,P4(1)32]
  cF      [196,F23] [209,F432] [210,F4(1)32]
  cI      [197,I23] [199,I2(1)3] [211,I432] [214,I4(1)32]


 ********************* SYMMETRY OF REFLECTION INTENSITIES *********************

 Analysis of symmetry related reflections is based on strong data within a
 TEST_RESOLUTION_RANGE=   10.00    5.00 Angstrom (defined in XDS.INP).

 The symmetry test is carried out for the enantiomorphous space groups above
 (omitting those with screw axes) that conform with the observed lattice
 symmetry. The test is quantified by the redundancy independent R-factor,
 Rmeas, defined by Diederichs & Karplus, Nature Struct. Biol. 4, 269-275 (1997).

 For computation of Rmeas a minimum number of symmetry-related reflections is
 required and set by the user (in XDS.INP) as the parameter
                      MIN_RFL_Rmeas=   50

 Low values for Rmeas should be obtained if the crystal possesses the symmetry
 elements of the tested space group. The decision whether the crystal's data
 satisfy the requirements of the tested space group is based on a constant set
 by the user (in XDS.INP) as the parameter
                      MAX_FAC_Rmeas=      2.
 which provides an upper limit for acceptable Rmeas as a multiple of the
 lowest Rmeas found for all tested space groups. If Rmeas is larger than this
 upper limit, the corresponding space group symmetry is considered violated
 by the observed data.

 XDS will select (*) that space group that requires a minimum number of unique
 reflections to account for the observed intensities at an acceptable Rmeas.

 UNIQUE   = number of unique reflections
 Rmeas    = redundancy independent R-factor (intensities)
 COMPARED = number of reflections used for calculating Rmeas

 SPACE-GROUP         UNIT CELL CONSTANTS            UNIQUE   Rmeas  COMPARED  LATTICE-
   NUMBER      a      b      c   alpha beta gamma                            CHARACTER

       1      78.1   90.8  116.1  89.9  90.1  89.8   11744    21.0    30631    31 aP


 ************ SELECTED SPACE GROUP AND UNIT CELL FOR THIS DATA SET ************

 SPACE_GROUP_NUMBER=   16
 UNIT_CELL_CONSTANTS=    78.06    90.81   116.12  90.000  90.000  90.000
 UNIT_CELL_A-AXIS=   -67.269    38.968     7.065
 UNIT_CELL_B-AXIS=    46.217    77.903     6.421
 UNIT_CELL_C-AXIS=    -4.873    12.269  -115.364
 REFLECTIONS H,K,L FROM "INTEGRATE.HKL" WILL BE REINDEXED BY
 H' = (  1*H +  0*K +  0*L)/ 1 +  0
 K' = (  0*H +  1*K +  0*L)/ 1 +  0
 L' = (  0*H +  0*K +  1*L)/ 1 +  0

 *****  4 EQUIVALENT POSITIONS IN SPACE GROUP # 16 *****

    If x',y',z' is an equivalent position to x,y,z, then
        x'=x*ML(1)+y*ML( 2)+z*ML( 3)+ML( 4)/12.0
        y'=x*ML(5)+y*ML( 6)+z*ML( 7)+ML( 8)/12.0
        z'=x*ML(9)+y*ML(10)+z*ML(11)+ML(12)/12.0

    #    1  2  3  4    5  6  7  8    9 10 11 12 
    1    1  0  0  0    0  1  0  0    0  0  1  0
    2   -1  0  0  0    0 -1  0  0    0  0  1  0
    3    1  0  0  0    0 -1  0  0    0  0 -1  0
    4   -1  0  0  0    0  1  0  0    0  0 -1  0

 ******************************************************************************
      MEAN DISCREPANCIES BETWEEN OBSERVED AND CALCULATED SPOT LOCATIONS
 ******************************************************************************

 The discrepancies in X- and Y-coordinates of the spots are depicted in the
 two images DX-CORRECTIONS.cbf and DY-CORRECTIONS.cbf for inspection with
 the XDS-Viewer.



 ******************************************************************************
  REFINEMENT OF DIFFRACTION PARAMETERS USING ALL IMAGES
 ******************************************************************************


 REFINED VALUES OF DIFFRACTION PARAMETERS DERIVED FROM    161028 INDEXED SPOTS
 REFINED PARAMETERS:   POSITION BEAM AXIS ORIENTATION CELL
 STANDARD DEVIATION OF SPOT    POSITION (PIXELS)     1.27
 STANDARD DEVIATION OF SPINDLE POSITION (DEGREES)    0.20
 SPACE GROUP NUMBER     16
 UNIT CELL PARAMETERS     78.449    91.201   116.818  90.000  90.000  90.000
 E.S.D. OF CELL PARAMETERS  3.1E-01 2.0E-01 3.1E-01 0.0E+00 0.0E+00 0.0E+00
 REC. CELL PARAMETERS   0.012747  0.010965  0.008560  90.000  90.000  90.000
 COORDINATES OF UNIT CELL A-AXIS   -67.462    39.340     7.453
 COORDINATES OF UNIT CELL B-AXIS    46.349    78.296     6.259
 COORDINATES OF UNIT CELL C-AXIS    -5.507    12.534  -116.013
 CRYSTAL MOSAICITY (DEGREES)     0.212
 LAB COORDINATES OF ROTATION AXIS  0.999995 -0.000870 -0.003156
 DIRECT BEAM COORDINATES (REC. ANGSTROEM)   0.001254 -0.000914  0.782073
 DETECTOR COORDINATES (PIXELS) OF DIRECT BEAM    1216.63   1257.19
 DETECTOR ORIGIN (PIXELS) AT                     1214.61   1258.67
 CRYSTAL TO DETECTOR DISTANCE (mm)       217.50
 LAB COORDINATES OF DETECTOR X-AXIS  1.000000  0.000000  0.000000
 LAB COORDINATES OF DETECTOR Y-AXIS  0.000000  1.000000  0.000000


 THE DATA COLLECTION STATISTICS REPORTED BELOW ASSUMES:
 SPACE_GROUP_NUMBER=   16
 UNIT_CELL_CONSTANTS=    78.45    91.20   116.82  90.000  90.000  90.000


 ******************************************************************************
        MEAN INTENSITY AS FUNCTION OF SPINDLE POSITION WITHIN DATA IMAGE
 ******************************************************************************

 This statistics could serve as a diagnostic tool for detecting
 shutter problems (suggested by Kay Diederichs).
 Data are corrected for this effect if PATCH_SHUTTER_PROBLEM=TRUE in XDS.INP.
 ===> Selected : PATCH_SHUTTER_PROBLEM=FALSE

 INTERVAL = Angular interval in units of the oscillation range
            covered by a data image.
 NUMBER   = Number of reflections in each interval
 INTENSITY= Mean intensity of the reflections in each interval
 FACTOR   = Correction factor applied to intensities;
            INTENSITY(overall)/INTENSITY(interval)

   INTERVAL     NUMBER      INTENSITY      FACTOR
 -0.05  0.05     64389        121.550       0.999
  0.75  0.85     64761        119.712       1.015
  0.85  0.95     64319        121.528       1.000

 -0.05  0.95    643779        121.480       1.000


 ******************************************************************************
           CORRECTION FACTORS AS FUNCTION OF IMAGE NUMBER & RESOLUTION
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED     1440
 DEGREES OF FREEDOM OF CHI^2 FIT            151937.7
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      1.265
 NUMBER OF CYCLES CARRIED OUT                      4

 CORRECTION FACTORS for visual inspection by XDS-Viewer DECAY.cbf           
 XMIN=     0.7 XMAX=  1799.4 NXBIN=   72
 YMIN= 0.00041 YMAX= 0.22550 NYBIN=   20
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     178126


 ******************************************************************************
  CORRECTION FACTORS AS FUNCTION OF X (fast) & Y(slow) IN THE DETECTOR PLANE
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED     3540
 DEGREES OF FREEDOM OF CHI^2 FIT            151733.6
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      1.254
 NUMBER OF CYCLES CARRIED OUT                      4

 CORRECTION FACTORS for visual inspection by XDS-Viewer MODPIX.cbf          
 XMIN=   329.9 XMAX=  2107.4 NXBIN=   59
 YMIN=   360.0 YMAX=  2153.1 NYBIN=   60
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     178126


 ******************************************************************************
   CORRECTION FACTORS AS FUNCTION OF IMAGE NUMBER & DETECTOR SURFACE POSITION
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED      936
 DEGREES OF FREEDOM OF CHI^2 FIT            152001.0
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      1.222
 NUMBER OF CYCLES CARRIED OUT                      3

 CORRECTION FACTORS for visual inspection by XDS-Viewer ABSORP.cbf          
 XMIN=     0.7 XMAX=  1799.4 NXBIN=   72
 DETECTOR_SURFACE_POSITION=    1219    1257
 DETECTOR_SURFACE_POSITION=    1521    1561
 DETECTOR_SURFACE_POSITION=     917    1561
 DETECTOR_SURFACE_POSITION=     917     952
 DETECTOR_SURFACE_POSITION=    1521     952
 DETECTOR_SURFACE_POSITION=    1902    1542
 DETECTOR_SURFACE_POSITION=    1502    1946
 DETECTOR_SURFACE_POSITION=     936    1946
 DETECTOR_SURFACE_POSITION=     535    1542
 DETECTOR_SURFACE_POSITION=     535     971
 DETECTOR_SURFACE_POSITION=     936     567
 DETECTOR_SURFACE_POSITION=    1502     567
 DETECTOR_SURFACE_POSITION=    1902     971
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     178126


 ******************************************************************************
    CORRECTION PARAMETERS FOR THE STANDARD ERROR OF REFLECTION INTENSITIES
 ******************************************************************************

 The variance v0(I) of the intensity I obtained from counting statistics is
 replaced by v(I)=a*(v0(I)+b*I^2). The model parameters a, b are chosen to
 minimize the discrepancies between v(I) and the variance estimated from
 sample statistics of symmetry related reflections. This model implicates
 an asymptotic limit ISa=1/SQRT(a*b) for the highest I/Sigma(I) that the
 experimental setup can produce (Diederichs (2010) Acta Cryst D66, 733-740).

     a        b          ISa
 1.421E+00  3.868E-03   13.49


 ******************************************************************************
      STANDARD ERROR OF REFLECTION INTENSITIES AS FUNCTION OF RESOLUTION
      FOR DATA SET  XDS_ASCII.HKL                                     
 ******************************************************************************

  I/Sigma  = mean intensity/Sigma of a reflection in shell
  Chi^2    = goodness of fit between sample variances of
             symmetry-related intensities and their errors
             (Chi^2 = 1 for perfect agreement)
  R-FACTOR
  observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
  expected = expected R-FACTOR derived from Sigma(I)

   NUMBER  = number of reflections in resolution shell
             used for calculation of R-FACTOR
  ACCEPTED = number of accepted reflections
  REJECTED = number of rejected reflections (MISFITS),
             recognized by comparison with symmetry-related
             reflections.

 RESOLUTION RANGE  I/Sigma  Chi^2  R-FACTOR  R-FACTOR  NUMBER ACCEPTED REJECTED
                                   observed  expected

   49.186  19.374    11.26   1.28      6.00      5.55     747     747      86
    2.127   2.116     0.54   0.62    119.54    152.94    9108    9117      26
    2.116   2.106     0.61   0.63    105.64    136.67    5002    5040      14
   --------------------------------------------------------------------------
   49.186   2.106     3.41   0.87     14.76     15.53  632694  632942   10837



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR VARIOUS SUBSETS OF INCLUDED DATA IMAGES
 ******************************************************************************

 Data set statistics is reported below several times, each with a different
 upper limit on the number of images included. This provides the user with
 the information for deciding which data images should be excluded from the
 final data set because of radiation damage or other defects. If the user
 decides for a subset of "good" images that differs from the specification
 as given by the input parameter (see DATA_RANGE= in XDS.INP) the CORRECT-
 step of XDS must be repeated with the new parameter values for DATA_RANGE=.


 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     201
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27        2558    1953      3581       54.5%       3.2%      4.4%     1210   12.27      4.6%    99.7*   -11    0.615     117
     4.45        4644    3672      6362       57.7%       4.1%      4.6%     1940   10.73      5.7%    99.1*   -23    0.592     127
     3.64        5676    4493      8238       54.5%       4.3%      4.8%     2363   10.60      6.1%    99.3*     8    0.770     127
     3.16        7368    5777      9706       59.5%       5.0%      5.8%     3170    7.87      7.0%    99.2*    21    0.831     188
     2.82        8899    6954     11068       62.8%       6.6%      7.8%     3867    5.59      9.3%    98.8*     5    0.771     278
     2.58        8905    7122     12182       58.5%       8.6%     10.8%     3542    4.01     12.1%    98.1*     4    0.691     266
     2.39       10258    8199     13232       62.0%      13.1%     15.8%     4096    2.76     18.5%    95.7*    -2    0.695     309
     2.23       11144    9031     14235       63.4%      18.5%     22.8%     4198    1.97     26.1%    91.6*    -7    0.645     331
     2.11       10255    8570     15187       56.4%      26.1%     31.9%     3330    1.43     36.7%    85.4*    -9    0.610     270
    total       69707   55771     93791       59.5%       5.9%      7.0%    27716    4.96      8.3%    99.5*     0    0.691    2013


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES     71786
 NUMBER OF REJECTED MISFITS                              2079
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                        69707
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  55771



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     401
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27        5184    3117      3581       87.0%       3.2%      4.4%     4060   13.67      4.5%    99.7*    -6    0.623     569
     4.45        9538    5760      6362       90.5%       4.5%      4.9%     7215   12.01      6.3%    99.0*   -17    0.629     882
     3.64       11564    7177      8238       87.1%       5.0%      5.3%     8320   11.06      6.8%    99.3*   -16    0.635     827
     3.16       14966    8843      9706       91.1%       6.7%      7.5%    11504    8.12      9.1%    99.0*    -7    0.656    1390
     2.82       17795   10361     11068       93.6%      10.1%     12.2%    13803    5.55     13.8%    98.2*    -6    0.653    1654
     2.58       17802   10892     12182       89.4%      14.6%     18.2%    12776    3.86     19.8%    96.7*    -4    0.624    1211
     2.39       20544   12084     13232       91.3%      23.4%     30.0%    15686    2.66     32.0%    91.2*    -1    0.635    1796
     2.23       22254   13052     14235       91.7%      35.8%     46.4%    16926    1.90     48.6%    82.8*    -2    0.608    1946
     2.11       20563   12756     15187       84.0%      49.9%     63.9%    14261    1.35     67.6%    72.6*    -4    0.600    1221
    total      140210   84042     93791       89.6%       8.1%      9.8%   104551    5.19     11.1%    99.4*    -6    0.629   11496


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    143153
 NUMBER OF REJECTED MISFITS                              2943
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       140210
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  84042



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     601
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27        7812    3250      3581       90.8%       4.6%      5.0%     7156   16.08      5.7%    99.6*     1    0.739     968
     4.45       14429    5930      6362       93.2%       5.6%      5.6%    13134   14.15      7.0%    99.2*    -4    0.729    1812
     3.64       17535    7628      8238       92.6%       6.1%      6.1%    15657   12.59      7.8%    99.2*    -7    0.735    2165
     3.16       22517    9224      9706       95.0%       8.6%      9.3%    20655    9.24     10.7%    98.9*    -7    0.696    2976
     2.82       26664   10654     11068       96.3%      13.3%     15.9%    24651    6.26     16.6%    98.2*    -6    0.655    3569
     2.58       26618   11608     12182       95.3%      19.5%     24.4%    23847    4.22     24.6%    96.2*    -1    0.644    3451
     2.39       30788   12602     13232       95.2%      31.3%     40.3%    28319    2.95     39.0%    91.0*    -4    0.612    4208
     2.23       33315   13530     14235       95.0%      47.8%     62.2%    30574    2.10     59.3%    83.2*    -3    0.604    4502
     2.11       30916   13859     15187       91.3%      64.1%     82.8%    27311    1.44     81.2%    71.0*    -5    0.582    4041
    total      210594   88285     93791       94.1%      10.4%     12.1%   191304    5.87     13.0%    99.4*    -4    0.647   27692


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    214239
 NUMBER OF REJECTED MISFITS                              3645
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       210594
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  88285



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     801
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       10487    3384      3581       94.5%       5.2%      5.3%    10135   18.27      6.2%    99.6*     7    0.839    1237
     4.45       19391    6214      6362       97.7%       5.8%      5.8%    18848   16.11      7.0%    99.4*     4    0.795    2454
     3.64       23556    8045      8238       97.7%       6.5%      6.2%    22731   14.38      7.9%    99.3*     1    0.808    3109
     3.16       30144    9576      9706       98.7%       8.7%      9.1%    29553   10.58     10.4%    99.1*     2    0.768    4091
     2.82       35642   11024     11068       99.6%      12.9%     15.0%    35061    7.20     15.5%    98.5*     1    0.711    4782
     2.58       35581   12073     12182       99.1%      18.8%     23.0%    34574    4.80     22.9%    97.0*     0    0.672    4937
     2.39       41136   13121     13232       99.2%      29.3%     37.3%    40285    3.34     35.2%    93.7*    -2    0.639    5647
     2.23       44498   14126     14235       99.2%      43.4%     55.9%    43514    2.39     51.9%    88.5*    -1    0.623    6008
     2.11       41411   14555     15187       95.8%      58.3%     75.3%    39859    1.64     71.1%    78.9*    -2    0.610    5779
    total      281846   92118     93791       98.2%      10.5%     11.9%   274560    6.70     12.7%    99.4*     0    0.690   38044


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    286209
 NUMBER OF REJECTED MISFITS                              4363
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       281846
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  92118



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1    1000
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       13189    3532      3581       98.6%       5.3%      5.4%    13089   19.99      6.2%    99.6*    19*   0.936    1428
     4.45       24342    6352      6362       99.8%       6.0%      5.9%    24252   17.96      7.0%    99.6*    11*   0.873    2840
     3.64       29544    8183      8238       99.3%       6.8%      6.3%    29297   16.15      7.9%    99.4*     5    0.855    3612
     3.16       37707    9672      9706       99.6%       9.1%      9.1%    37569   11.87     10.5%    99.1*     1    0.780    4478
     2.82       44558   11064     11068      100.0%      13.2%     14.6%    44513    8.05     15.3%    98.7*    -2    0.710    5225
     2.58       44494   12156     12182       99.8%      19.0%     22.5%    44187    5.28     22.2%    97.2*    -2    0.680    5552
     2.39       51431   13196     13232       99.7%      29.7%     37.1%    51222    3.63     34.4%    94.4*    -1    0.644    6173
     2.23       55669   14200     14235       99.8%      43.8%     55.3%    55424    2.58     50.7%    89.3*    -1    0.635    6608
     2.11       51728   14680     15187       96.7%      60.6%     76.3%    51051    1.74     71.2%    78.9*    -2    0.623    6506
    total      352662   93035     93791       99.2%      10.9%     12.0%   350604    7.47     12.6%    99.5*     2    0.710   42422


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    357840
 NUMBER OF REJECTED MISFITS                              5178
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       352662
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  93035



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1    1200
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       15901    3547      3581       99.1%       5.5%      5.6%    15838   21.95      6.1%    99.7*    24*   1.015    1474
     4.45       29300    6357      6362       99.9%       6.4%      6.1%    29268   19.70      7.2%    99.6*    14*   0.921    2890
     3.64       35529    8199      8238       99.5%       7.2%      6.5%    35385   17.64      8.2%    99.5*     9    0.889    3723
     3.16       45331    9677      9706       99.7%       9.6%      9.5%    45237   12.82     10.8%    99.3*     1    0.794    4516
     2.82       53532   11064     11068      100.0%      14.2%     15.6%    53500    8.59     16.0%    98.9*    -1    0.713    5238
     2.58       53420   12157     12182       99.8%      20.6%     24.5%    53237    5.59     23.4%    97.4*    -1    0.674    5674
     2.39       61787   13204     13232       99.8%      32.3%     40.5%    61666    3.82     36.5%    94.7*    -2    0.638    6264
     2.23       66863   14213     14235       99.8%      47.7%     60.4%    66766    2.71     53.8%    90.2*    -1    0.632    6763
     2.11       62044   14702     15187       96.8%      66.5%     84.0%    61692    1.81     76.0%    80.2*    -2    0.625    6826
    total      423707   93120     93791       99.3%      11.6%     12.7%   422589    8.06     13.1%    99.6*     3    0.718   43368


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    429540
 NUMBER OF REJECTED MISFITS                              5833
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       423707
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  93120



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1    1400
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       18587    3562      3581       99.5%       5.5%      5.7%    18545   23.73      6.1%    99.8*    31*   1.071    1501
     4.45       34377    6358      6362       99.9%       6.6%      6.2%    34351   21.31      7.3%    99.8*    17*   0.947    2896
     3.64       41629    8224      8238       99.8%       7.4%      6.7%    41549   18.86      8.3%    99.5*    11*   0.904    3796
     3.16       52955    9677      9706       99.7%      10.1%     10.1%    52868   13.57     11.1%    99.3*     2    0.782    4522
     2.82       62500   11064     11068      100.0%      15.3%     17.0%    62476    9.00     16.9%    99.0*    -1    0.724    5246
     2.58       62318   12171     12182       99.9%      22.6%     27.3%    62216    5.80     25.2%    97.7*    -2    0.675    5747
     2.39       72072   13204     13232       99.8%      36.0%     45.4%    71953    3.95     39.8%    95.2*    -2    0.641    6266
     2.23       78079   14213     14235       99.8%      53.4%     68.1%    77987    2.78     59.1%    91.2*     0    0.641    6768
     2.11       72410   14725     15187       97.0%      73.8%     94.3%    72169    1.86     82.6%    82.1*    -2    0.626    6922
    total      494927   93198     93791       99.4%      12.2%     13.5%   494114    8.54     13.6%    99.7*     4    0.726   43664


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    501184
 NUMBER OF REJECTED MISFITS                              6253
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       494931
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  93199



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1    1600
 ******************************************************************************


          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       21201    3562      3581       99.5%       5.7%      5.7%    21170   25.24      6.2%    99.8*    36*   1.138    1505
     4.45       39252    6359      6362      100.0%       6.8%      6.4%    39241   22.50      7.5%    99.8*    22*   0.990    2908
     3.64       47524    8226      8238       99.9%       7.8%      7.0%    47470   19.78      8.6%    99.5*    14*   0.929    3817
     3.16       60507    9678      9706       99.7%      10.8%     10.8%    60428   14.09     11.8%    99.4*     3    0.795    4530
     2.82       71404   11064     11068      100.0%      17.1%     19.2%    71381    9.23     18.6%    99.0*    -1    0.717    5247
     2.58       71127   12171     12182       99.9%      26.0%     31.5%    71049    5.92     28.5%    97.7*    -1    0.674    5769
     2.39       82271   13205     13232       99.8%      41.9%     52.2%    82164    3.99     45.8%    95.2*    -2    0.650    6277
     2.23       89091   14213     14235       99.8%      61.3%     77.1%    89011    2.80     66.8%    91.1*    -1    0.647    6779
     2.11       82727   14733     15187       97.0%      84.1%    107.3%    82546    1.87     92.7%    82.0*    -2    0.631    6981
    total      565104   93211     93791       99.4%      13.3%     14.6%   564460    8.86     14.5%    99.7*     5    0.736   43813


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    572456
 NUMBER OF REJECTED MISFITS                              7348
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       565108
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  93212



 ******************************************************************************
  STATISTICS OF SAVED DATA SET "XDS_ASCII.HKL" (DATA_RANGE=       1    1800)
 FILE TYPE:         XDS_ASCII      MERGE=FALSE          FRIEDEL'S_LAW=FALSE
 ******************************************************************************

   REFLECTIONS OF TYPE H,0,0  0,K,0  0,0,L OR EXPECTED TO BE ABSENT (*)
   --------------------------------------------------------------------

   H    K    L  RESOLUTION  INTENSITY     SIGMA    INTENSITY/SIGMA  #OBSERVED

    0    0    3    38.939  -0.2572E+01  0.6842E+00        -3.76           4 
   36    0    0     2.179   0.5343E+02  0.8036E+01         6.65           4
   37    0    0     2.120  -0.1804E+01  0.7189E+01        -0.25           4 

          COMPLETENESS AND QUALITY OF DATA SET
          ------------------------------------

 R-FACTOR
 observed = (SUM(ABS(I(h,i)-I(h))))/(SUM(I(h,i)))
 expected = expected R-FACTOR derived from Sigma(I)

 COMPARED = number of reflections used for calculating R-FACTOR
 I/SIGMA  = mean of intensity/Sigma(I) of unique reflections
            (after merging symmetry-related observations)
 Sigma(I) = standard deviation of reflection intensity I
            estimated from sample statistics

 R-meas   = redundancy independent R-factor (intensities)
            Diederichs & Karplus (1997), Nature Struct. Biol. 4, 269-275.

 CC(1/2)  = percentage of correlation between intensities from
            random half-datasets. Correlation significant at
            the 0.1% level is marked by an asterisk.
            Karplus & Diederichs (2012), Science 336, 1030-33
 Anomal   = percentage of correlation between random half-sets
  Corr      of anomalous intensity differences. Correlation
            significant at the 0.1% level is marked.
 SigAno   = mean anomalous difference in units of its estimated
            standard deviation (|F(+)-F(-)|/Sigma). F(+), F(-)
            are structure factor estimates obtained from the
            merged intensity observations in each parity class.
  Nano    = Number of unique reflections used to calculate
            Anomal_Corr & SigAno. At least two observations
            for each (+ and -) parity are required.

       NOTE:      Friedel pairs are treated as different reflections.

 SUBSET OF INTENSITY DATA WITH SIGNAL/NOISE >= -3.0 AS FUNCTION OF RESOLUTION
 RESOLUTION     NUMBER OF REFLECTIONS    COMPLETENESS R-FACTOR  R-FACTOR COMPARED I/SIGMA   R-meas  CC(1/2)  Anomal  SigAno   Nano
   LIMIT     OBSERVED  UNIQUE  POSSIBLE     OF DATA   observed  expected                                      Corr

     6.27       23802    3563      3581       99.5%       5.9%      5.8%    23793   26.62      6.4%    99.8*    40*   1.198    1514
     4.45       43902    6362      6362      100.0%       7.3%      6.5%    43902   23.54      7.9%    99.7*    25*   1.049    2918
     3.64       53084    8227      8238       99.9%       8.5%      7.3%    53077   20.57      9.3%    99.6*    16*   0.971    3845
     3.16       67886    9693      9706       99.9%      12.3%     11.8%    67850   14.44     13.4%    99.3*     3    0.802    4562
     2.82       80235   11064     11068      100.0%      20.1%     22.0%    80231    9.38     21.7%    98.9*    -3    0.718    5261
     2.58       79726   12179     12182      100.0%      31.7%     36.7%    79697    5.94     34.5%    97.4*    -2    0.679    5805
     2.39       91810   13225     13232       99.9%      50.3%     58.4%    91763    3.95     54.5%    94.5*    -2    0.666    6323
     2.23       99642   14229     14235      100.0%      72.2%     86.0%    99612    2.75     78.1%    89.6*    -1    0.672    6826
     2.11       92851   14747     15187       97.1%      97.2%    120.4%    92765    1.84    106.1%    80.2*    -3    0.639    7055
    total      632938   93289     93791       99.5%      14.8%     15.5%   632690    9.09     16.0%    99.7*     5    0.755   44109


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    643779
 NUMBER OF REJECTED MISFITS                             10837
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       632942
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  93290



 ******************************************************************************
    WILSON STATISTICS OF DATA SET  "XDS_ASCII.HKL"
 ******************************************************************************

 Data is divided into resolution shells and a straight line 
 A - 2*B*SS is fitted to log<I>, where
   RES    = mean resolution (Angstrom) in shell
   SS     = mean of (sin(THETA)/LAMBDA)**2 in shell
   <I>    = mean reflection intensity in shell
   BO     = (A - log<I>)/(2*SS)
    #     = number of reflections in resolution shell

   WILSON LINE (using all data) : A=   6.678 B=  39.462 CORRELATION=  0.98
      #      RES      SS        <I>       log(<I>)       BO
     720    11.799   0.002  6.2258E+02       6.434      68.0
    3690     2.194   0.052  1.4362E+01       2.665      38.6
    3541     2.135   0.055  1.1854E+01       2.473      38.4


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF  CENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         3<I>**2     15<I>**3    105<I>**4 

     249    11.799        2.012        3.753        6.101
     232     2.135        2.091        3.986        7.347
    4735   overall        1.800        3.628        7.348


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF ACENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         2<I>**2      6<I>**3     24<I>**4 

     471    11.799        1.279        2.080        3.684
    3309     2.135        1.376        2.451        5.237
   44302   overall        1.380        2.613        6.109

   ======= CUMULATIVE INTENSITY DISTRIBUTION =======
 DEFINITIONS:
   <I>    = mean reflection intensity
 Na(Z)exp = expected number of acentric reflections with I <= Z*<I>
 Na(Z)obs = observed number of acentric reflections with I <= Z*<I>
 Nc(Z)exp = expected number of  centric reflections with I <= Z*<I>
 Nc(Z)obs = observed number of  centric reflections with I <= Z*<I>



 Nc(Z)obs/Nc(Z)exp versus resolution and Z (0.1-1.0)
      #      RES     0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   0.9   1.0

     249    11.799  1.62  1.45  1.34  1.28  1.21  1.19  1.14  1.12  1.09  1.07
     253     2.194  1.10  0.99  0.97  0.94  0.96  0.99  1.00  0.97  1.03  1.04
     232     2.135  0.99  0.85  0.84  0.85  0.84  0.91  0.94  0.94  0.95  0.97
    4735   overall  0.98  1.01  1.04  1.05  1.06  1.08  1.08  1.07  1.07  1.06


 Na(Z)obs/Na(Z)exp versus resolution and Z (0.1-1.0)
      #      RES     0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   0.9   1.0

     471    11.799  1.63  1.56  1.38  1.35  1.27  1.22  1.17  1.13  1.10  1.08
    3437     2.194  1.77  1.26  1.14  1.09  1.08  1.07  1.08  1.07  1.07  1.06
    3309     2.135  1.82  1.24  1.10  1.04  1.02  1.02  1.03  1.04  1.04  1.04
   44302   overall  1.27  1.17  1.15  1.14  1.13  1.12  1.11  1.10  1.09  1.08


 List of    117 reflections *NOT* obeying Wilson distribution (Z> 10.0)

   h    k    l     RES      Z     Intensity    Sigma

   23   26    9    2.40   28.65  0.6813E+03  0.2005E+02 "alien"
    5   30   17    2.74   10.04  0.4354E+03  0.1156E+02 "alien"
   30   17    9    2.31   10.01  0.2055E+03  0.1419E+02 "alien"


 List of    117 reflections *NOT* obeying Wilson distribution (sorted by resolution)
 Ice rings could occur at (Angstrom):
 3.897,3.669,3.441, 2.671,2.249,2.072, 1.948,1.918,1.883,1.721

   h    k    l     RES      Z     Intensity    Sigma

   20   36    8    2.11   11.21  0.1328E+03  0.8748E+01
    1    1   13    8.89   11.79  0.4940E+04  0.9203E+02
    6    3    8    9.28   11.61  0.7229E+04  0.1343E+03

 NUMBER OF UNIQUE ALIEN REFLECTIONS WITH A Z-SCORE ABOVE LIMIT         1
 (ALIENS ABOVE LIMIT (REJECT_ALIEN=      20.0) ARE MARKED INVALID)

 NUMBER OF REFLECTION RECORDS ON OUTPUT FILE "XDS_ASCII.HKL"      643779
 NUMBER OF ACCEPTED OBSERVATIONS (INCLUDING SYSTEMATIC ABSENCES)  632934
 NUMBER OF REJECTED MISFITS & ALIENS (marked by -1*SIGMA(IOBS))    10845


 cpu time used                223.6 sec
 elapsed wall-clock time       25.8 sec
