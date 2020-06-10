
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
 *  31        aP          0.7      78.1   90.8  116.1  89.9  90.1  89.8   -1  0  0  0  0  1  0  0  0  0 -1  0
 *  34        mP          1.6      78.1  116.1   90.8  90.1  90.2  90.1   -1  0  0  0  0  0 -1  0  0 -1  0  0
 *  35        mP          3.0      90.8   78.1  116.1  90.1  90.1  90.2    0 -1  0  0 -1  0  0  0  0  0 -1  0
 *  33        mP          3.1      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
 *  32        oP          3.9      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
    14        mC        176.7     119.5  120.0  116.1  90.0  90.1  81.4    1  1  0  0 -1  1  0  0  0  0  1  0
    13        oC        178.2     119.5  120.0  116.1  90.0  90.1  81.4    1  1  0  0 -1  1  0  0  0  0  1  0
    10        mC        179.0     120.0  119.5  116.1  89.9  90.0  98.6   -1  1  0  0 -1 -1  0  0  0  0  1  0
    11        tP        180.5      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
    39        mC        248.5     197.4   78.1  116.1  90.1  90.1  66.9   -1 -2  0  0 -1  0  0  0  0  0 -1  0
    38        oC        249.3      78.1  197.4  116.1  89.9  90.1 113.1   -1  0  0  0  1  2  0  0  0  0 -1  0
    29        mC        249.7      78.1  197.4  116.1  89.9  89.9  66.9   -1  0  0  0 -1 -2  0  0  0  0  1  0
    37        mC        251.5     244.9   78.1   90.8  90.2  90.1  71.5    1  0  2  0  1  0  0  0  0  1  0  0
    28        mC        251.8      78.1  245.1   90.8  90.0  90.2  71.4    1  0  0  0  1  0 -2  0  0  1  0  0
    36        oC        252.4      78.1  244.9   90.8  89.9  90.2 108.5    1  0  0  0 -1  0 -2  0  0  1  0  0
    41        mC        339.8     249.3   90.8   78.1  90.2  90.1  68.7    0 -1 -2  0  0 -1  0  0 -1  0  0  0
    30        mC        340.1      90.8  249.3   78.1  90.1  90.2  68.7    0  1  0  0  0  1  2  0  1  0  0  0
    40        oC        340.5      90.8  249.3   78.1  89.9  90.2 111.3    0 -1  0  0  0  1  2  0 -1  0  0  0
    12        hP        425.9      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
    25        mC        431.3     147.3  147.5   78.1  89.9  90.2  76.1    0  1  1  0  0 -1  1  0  1  0  0  0
    20        mC        432.1     147.3  147.5   78.1  90.1  90.2 103.9    0 -1 -1  0  0 -1  1  0 -1  0  0  0
    23        oC        432.8     147.3  147.5   78.1  89.9  90.2  76.1    0  1  1  0  0 -1  1  0  1  0  0  0
    21        tP        433.6      90.8  116.1   78.1  90.1  90.2  90.1    0  1  0  0  0  0  1  0  1  0  0  0
    27        mC        499.8     197.4   78.1  147.3  89.8 124.4  66.9    1  2  0  0  1  0  0  0  0 -1 -1  0
    42        oI        589.0      78.1   90.8  261.0 110.2 107.3  90.2   -1  0  0  0  0 -1  0  0  1  1  2  0
     4        hR        608.0     120.0  140.0  166.5 108.5  83.8 111.4    1 -1  0  0 -1  0  1  0 -1 -1 -1  0
     3        cP        608.8      78.1   90.8  116.1  90.1  90.1  90.2    1  0  0  0  0  1  0  0  0  0  1  0
     2        hR        610.3     119.5  139.9  167.0 108.4  83.9 111.2    1  1  0  0 -1  0 -1  0 -1  1  1  0
    26        oF        624.0      78.1  197.4  245.1  82.8 108.6 113.1   -1  0  0  0  1  2  0  0  1  0 -2  0
    15        tI        677.3      78.1   90.8  261.0  69.8  72.7  90.2    1  0  0  0  0  1  0  0  1  1  2  0
    22        hP        770.2      90.8  116.1   78.1  90.1  90.2  90.1    0  1  0  0  0  0  1  0  1  0  0  0
     5        cI        773.8     139.9  119.5  147.3  62.3  49.3  68.8    1  0  1  0  1  1  0  0  0  1  1  0
     1        cF        860.9     166.5  167.0  166.7 114.1  91.7 124.2   -1 -1 -1  0 -1  1  1  0  1  1 -1  0
     9        hR        925.6      78.1  119.5  368.4  92.7 102.3 130.6   -1  0  0  0  1  1  0  0  1 -1 -3  0
    19        oI        929.5      78.1  147.3  166.7  77.6  62.2  89.8    1  0  0  0  0 -1 -1  0  1  1 -1  0
     6        tI        999.0     147.3  139.9  119.5  68.8  62.3  49.3    0  1  1  0  1  0  1  0  1  1  0  0
     7        tI        999.0     139.9  119.5  147.3  62.3  49.3  68.8    1  0  1  0  1  1  0  0  0  1  1  0
     8        oI        999.0     119.5  139.9  147.3  49.3  62.3  68.8   -1 -1  0  0 -1  0 -1  0  0 -1 -1  0
    16        oF        999.0     119.5  120.0  261.0  93.9 117.2  81.4   -1 -1  0  0  1 -1  0  0  1  1  2  0
    17        mC        999.0     120.0  119.5  139.9  68.8 111.4  81.4    1 -1  0  0 -1 -1  0  0 -1  0 -1  0
    18        tI        999.0     147.3  166.7   78.1  62.2  90.2 102.4    0 -1 -1  0 -1 -1  1  0 -1  0  0  0
    24        hR        999.0     228.9  147.5   78.1  89.9  70.3  95.1    1  2  1  0  0 -1  1  0  1  0  0  0
    43        mI        999.0     119.5  261.0   90.8 110.2 139.2  62.8    1  1  0  0  1  1  2  0  0 -1  0  0

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
   *  16      78.1   90.8  116.1  90.0  90.0  90.0    3346    19.9    39029    32 oP
       3      90.8   78.1  116.1  90.0  90.1  90.0    6281    20.4    36094    35 mP
       3      78.1   90.8  116.1  90.0  90.1  90.0    6250    19.9    36125    33 mP
       3      78.1  116.1   90.8  90.0  90.2  90.0    6145    19.9    36230    34 mP
       1      78.1   90.8  116.1  90.1  90.1  90.2   11744    21.0    30631    44 aP
 

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
  0.05  0.15     64228        120.929       1.005
  0.15  0.25     64281        118.845       1.022
  0.25  0.35     64303        119.088       1.020
  0.35  0.45     64591        123.298       0.985
  0.45  0.55     64206        128.793       0.943
  0.55  0.65     64362        119.676       1.015
  0.65  0.75     64339        121.393       1.001
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
   19.374  14.264    10.15   1.20      5.46      5.78    1346    1346      98
   14.264  11.813    11.41   1.20      5.39      5.71    1665    1665      92
   11.813  10.305    11.74   1.14      5.62      5.66    2008    2008     106
   10.305   9.258    11.58   1.17      5.60      5.66    2219    2219     112
    9.258   8.476    11.53   1.22      6.09      5.73    2600    2600     118
    8.476   7.864    10.85   1.21      5.63      5.81    2379    2379     153
    7.864   7.368    10.80   1.20      5.49      5.72    2274    2282     100
    7.368   6.955     9.53   1.25      6.20      6.03    2297    2297     101
    6.955   6.605     9.09   1.26      6.98      6.49    2906    2907     113
    6.605   6.303     8.96   1.34      7.45      6.46    2968    2968     194
    6.303   6.039     8.40   1.26      7.90      6.87    3072    3072     136
    6.039   5.805     8.82   1.22      7.36      6.62    3440    3440     144
    5.805   5.597     8.84   1.30      7.61      6.67    3507    3507     149
    5.597   5.409     8.26   1.24      7.71      7.07    3790    3790     167
    5.409   5.239     8.88   1.24      7.45      6.72    3747    3747     169
    5.239   5.085     8.87   1.22      7.29      6.72    4163    4163     196
    5.085   4.943     9.04   1.29      7.24      6.53    4033    4033     241
    4.943   4.812     9.18   1.31      6.94      6.38    4185    4185     205
    4.812   4.692     9.56   1.34      7.17      6.37    4404    4404     300
    4.692   4.580     9.48   1.35      7.19      6.37    4374    4374     331
    4.580   4.475     9.11   1.35      7.28      6.42    4620    4620     303
    4.475   4.378     9.14   1.29      7.10      6.45    4585    4585     309
    4.378   4.286     8.94   1.36      7.67      6.50    4745    4745     333
    4.286   4.200     8.94   1.36      8.06      6.83    4981    4981     303
    4.200   4.119     8.58   1.32      8.03      6.92    4910    4910     282
    4.119   4.043     8.65   1.29      7.89      6.90    4894    4894     221
    4.043   3.970     8.16   1.27      8.18      7.17    4739    4739     191
    3.970   3.902     7.97   1.35      8.85      7.36    3987    3990     212
    3.902   3.837     7.79   1.34      9.04      7.56    4633    4633     219
    3.837   3.775     7.63   1.31      9.13      7.79    4635    4635     203
    3.775   3.715     7.10   1.31     10.00      8.39    4836    4836     248
    3.715   3.659     7.09   1.30     10.28      8.65    5141    5145     231
    3.659   3.605     6.81   1.24     10.57      8.99    5624    5627     193
    3.605   3.553     6.85   1.26     10.58      9.06    5336    5340     213
    3.553   3.504     6.48   1.17     10.66      9.54    5870    5875     170
    3.504   3.457     6.15   1.12     11.26     10.38    5996    6006     129
    3.457   3.411     5.63   1.05     11.71     11.10    5862    5864     108
    3.411   3.367     5.59   1.04     12.18     11.43    6021    6025      80
    3.367   3.325     5.31   0.98     12.02     11.89    6398    6402      99
    3.325   3.284     4.88   0.95     13.82     13.91    6183    6183      65
    3.284   3.245     4.38   0.93     15.04     15.30    6420    6424      50
    3.245   3.207     4.24   0.89     15.02     15.60    6745    6745      69
    3.207   3.171     4.14   0.85     15.23     16.16    6476    6476      68
    3.171   3.136     4.11   0.90     15.77     16.24    6716    6716      48
    3.136   3.102     4.01   0.88     16.66     17.26    6857    6857      80
    3.102   3.069     3.78   0.86     17.33     18.70    7005    7009      37
    3.069   3.037     3.54   0.84     18.80     20.09    6977    6977      54
    3.037   3.005     3.48   0.83     18.91     20.63    6901    6901      37
    3.005   2.975     3.37   0.85     19.80     21.49    7460    7460      46
    2.975   2.946     3.09   0.82     21.74     23.91    7050    7050      26
    2.946   2.918     3.02   0.78     22.58     25.32    7187    7187      27
    2.918   2.890     3.08   0.75     21.48     24.63    7637    7637      25
    2.890   2.863     2.86   0.74     23.12     26.43    7505    7505      17
    2.863   2.837     2.89   0.79     23.44     25.91    7486    7486      27
    2.837   2.812     2.68   0.77     26.56     30.30    7594    7594      26
    2.812   2.787     2.55   0.75     26.60     30.81    6933    6933      24
    2.787   2.763     2.49   0.77     27.86     31.83    6651    6657      33
    2.763   2.740     2.41   0.73     28.34     33.16    6585    6589      15
    2.740   2.717     2.28   0.74     30.65     35.47    6791    6791      17
    2.717   2.695     2.11   0.73     33.24     38.88    6784    6784      36
    2.695   2.673     2.12   0.75     33.94     39.08    7167    7168      26
    2.673   2.652     2.21   0.74     31.81     36.61    7253    7253      43
    2.652   2.631     1.93   0.74     36.53     42.33    7737    7743      45
    2.631   2.611     2.03   0.76     34.40     39.25    7400    7403      55
    2.611   2.591     1.91   0.75     35.76     41.18    7876    7880      74
    2.591   2.572     1.68   0.73     40.99     48.11    7994    7999      76
    2.572   2.553     1.63   0.74     43.90     51.49    7806    7818      44
    2.553   2.534     1.48   0.71     47.49     56.79    8078    8083      54
    2.534   2.516     1.60   0.73     43.04     50.74    8148    8150      84
    2.516   2.498     1.54   0.80     45.57     51.41    8288    8295     116
    2.498   2.481     1.31   0.80     54.24     61.45    7920    7923      87
    2.481   2.464     1.40   0.78     49.51     56.68    8514    8520     128
    2.464   2.447     1.24   0.73     55.75     65.60    8329    8331      83
    2.447   2.431     1.28   0.75     53.91     63.02    8361    8364      80
    2.431   2.415     1.19   0.77     58.63     67.71    8765    8770      95
    2.415   2.399     1.22   0.76     56.20     65.29    8303    8303     120
    2.399   2.384     1.19   0.74     56.54     66.26    8574    8576     112
    2.384   2.369     1.10   0.74     61.22     71.90    8859    8860     105
    2.369   2.354     1.10   0.74     61.42     72.50    8607    8607      85
    2.354   2.339     1.09   0.75     63.71     74.41    8806    8815      94
    2.339   2.325     1.00   0.76     70.66     80.94    8682    8684      83
    2.325   2.311     1.00   0.73     68.32     81.34    8991    8994      67
    2.311   2.297     0.93   0.72     73.08     87.34    9033    9038      81
    2.297   2.284     0.86   0.69     79.14     96.03    9091    9093      53
    2.284   2.270     0.89   0.70     76.71     92.91    9110    9111      70
    2.270   2.257     0.86   0.73     82.29     97.22    9286    9289      71
    2.257   2.245     0.83   0.70     82.18     99.93    9002    9006      72
    2.245   2.232     0.74   0.68     89.98    111.32    9326    9326      64
    2.232   2.220     0.87   0.71     77.85     94.10    9219    9219      94
    2.220   2.207     0.81   0.71     83.95    101.09    8307    8310      80
    2.207   2.195     0.74   0.71     91.28    110.25    8730    8738      48
    2.195   2.184     0.68   0.66     95.19    119.03    8040    8045      35
    2.184   2.172     0.68   0.67     96.18    119.24    7794    7799      32
    2.172   2.160     0.69   0.65     96.86    121.17    8618    8623      45
    2.160   2.149     0.68   0.69     99.61    121.73    8823    8823      52
    2.149   2.138     0.63   0.70    107.64    131.29    8980    8983      34
    2.138   2.127     0.56   0.64    117.70    149.47    8887    8897      25
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
    0    0    4    29.204   0.2033E+04  0.7592E+02        26.78           4 
    0    0    5    23.364  -0.5208E+00  0.1134E+01        -0.46           4 
    0    0    6    19.470   0.2530E+03  0.9705E+01        26.07           4 
    0    0    7    16.688   0.2737E+01  0.1499E+01         1.83           4 
    0    0    8    14.602   0.4381E+02  0.3706E+01        11.82           2 
    0    0    9    12.980   0.2315E+01  0.1889E+01         1.23           4 
    0    0   10    11.682   0.2101E+03  0.8466E+01        24.82           4 
    0    0   11    10.620   0.2159E+01  0.2399E+01         0.90           4 
    0    0   12     9.735   0.5381E+04  0.1999E+03        26.92           4 
    0    0   13     8.986   0.3746E+01  0.2931E+01         1.28           4 
    0    0   14     8.344   0.2300E+03  0.1377E+02        16.71           2 
    0    0   15     7.788  -0.2586E+00  0.6040E+01        -0.04           1 
    0    0   16     7.301   0.6956E+01  0.5035E+01         1.38           2 
    0    0   18     6.490   0.1809E+02  0.4209E+01         4.30           4 
    0    0   19     6.148   0.1461E+01  0.4475E+01         0.33           4 
    0    0   20     5.841   0.1234E+04  0.4682E+02        26.36           4 
    0    0   21     5.563   0.7060E+00  0.4917E+01         0.14           4 
    0    0   22     5.310  -0.2350E+01  0.5350E+01        -0.44           4 
    0    0   23     5.079  -0.7122E+01  0.5741E+01        -1.24           4 
    0    0   24     4.867   0.2102E+04  0.7907E+02        26.59           4 
    0    0   25     4.673   0.9947E+01  0.6667E+01         1.49           4 
    0    0   26     4.493   0.6284E+04  0.2340E+03        26.85           4 
    0    0   27     4.327   0.3382E+01  0.7259E+01         0.47           4 
    0    0   28     4.172   0.1059E+03  0.1212E+02         8.74           2 
    0    0   29     4.028   0.3514E+01  0.1090E+02         0.32           2 
    0    0   30     3.894   0.3408E+03  0.2303E+02        14.80           2 
    0    0   31     3.768  -0.2320E+01  0.9133E+01        -0.25           4 
    0    0   32     3.651   0.2272E+02  0.9538E+01         2.38           4 
    0    0   33     3.540  -0.1042E+02  0.1030E+02        -1.01           4 
    0    0   34     3.436   0.4646E+01  0.1040E+02         0.45           4 
    0    0   35     3.338  -0.1038E+02  0.1051E+02        -0.99           4 
    0    0   36     3.245   0.1944E+03  0.1361E+02        14.28           4 
    0    0   37     3.157   0.5857E+01  0.1051E+02         0.56           4 
    0    0   38     3.074   0.7918E+02  0.1146E+02         6.91           4 
    0    0   39     2.995  -0.7767E+01  0.1033E+02        -0.75           4 
    0    0   40     2.920   0.1369E+03  0.1219E+02        11.23           4 
    0    0   41     2.849   0.6640E+01  0.9744E+01         0.68           4 
    0    0   42     2.781   0.1066E+01  0.1383E+02         0.08           2 
    0    0   43     2.717   0.6966E+00  0.1457E+02         0.05           2 
    0    0   44     2.655   0.2741E+02  0.1015E+02         2.70           4 
    0    0   45     2.596   0.4026E+01  0.1014E+02         0.40           4 
    0    0   46     2.540  -0.8786E+01  0.1037E+02        -0.85           4 
    0    0   47     2.485  -0.2636E+01  0.1025E+02        -0.26           4 
    0    0   48     2.434  -0.8644E+00  0.1013E+02        -0.09           4 
    0    0   49     2.384  -0.3313E+01  0.1036E+02        -0.32           4 
    0    0   50     2.336   0.1988E+02  0.1048E+02         1.90           4 
    0    0   51     2.291   0.1215E+02  0.1115E+02         1.09           4 
    0    0   52     2.246  -0.1439E+02  0.1196E+02        -1.20           4 
    0    0   53     2.204  -0.3700E+01  0.2332E+02        -0.16           1 
    0    0   54     2.163   0.1014E+02  0.1366E+02         0.74           3 
    0    0   55     2.124   0.1074E+02  0.1142E+02         0.94           4 
    0    2    0    45.600   0.8249E+03  0.4344E+02        18.99           2 
    0    3    0    30.400   0.4666E+00  0.9422E+00         0.50           4 
    0    4    0    22.800   0.2352E+04  0.8757E+02        26.86           4 
    0    5    0    18.240  -0.1354E+01  0.1512E+01        -0.90           4 
    0    6    0    15.200   0.2523E+03  0.1000E+02        25.22           4 
    0    7    0    13.029  -0.1389E+01  0.2052E+01        -0.68           4 
    0    8    0    11.400   0.5852E+03  0.2240E+02        26.13           4 
    0    9    0    10.133   0.3843E+01  0.2803E+01         1.37           4 
    0   10    0     9.120   0.2023E+04  0.7582E+02        26.68           4 
    0   11    0     8.291  -0.1960E+00  0.3288E+01        -0.06           4 
    0   12    0     7.600   0.1380E+04  0.6020E+02        22.93           3 
    0   13    0     7.015   0.3153E+00  0.5231E+01         0.06           2 
    0   14    0     6.514   0.4807E+03  0.2689E+02        17.88           2 
    0   15    0     6.080   0.1992E+01  0.4015E+01         0.50           4 
    0   16    0     5.700   0.6900E+02  0.5499E+01        12.55           4 
    0   17    0     5.365   0.4591E+01  0.4530E+01         1.01           4 
    0   18    0     5.067   0.6585E+03  0.2616E+02        25.18           4 
    0   19    0     4.800   0.8247E+01  0.5221E+01         1.58           4 
    0   20    0     4.560   0.7259E+02  0.6861E+01        10.58           4 
    0   21    0     4.343   0.4475E+01  0.5863E+01         0.76           4 
    0   22    0     4.145   0.1970E+02  0.6705E+01         2.94           3 
    0   23    0     3.965  -0.3960E+01  0.6839E+01        -0.58           4 
    0   24    0     3.800   0.2585E+04  0.1139E+03        22.69           3 
    0   25    0     3.648  -0.2332E+01  0.7936E+01        -0.29           4 
    0   26    0     3.508   0.5485E+02  0.1470E+02         3.73           2 
    0   27    0     3.378  -0.4951E+01  0.1209E+02        -0.41           1 
    0   28    0     3.257   0.1354E+04  0.5393E+02        25.11           4 
    0   29    0     3.145  -0.9074E+00  0.8084E+01        -0.11           4 
    0   30    0     3.040   0.2178E+03  0.1360E+02        16.01           4 
    0   31    0     2.942   0.1920E+00  0.7806E+01         0.02           4 
    0   32    0     2.850   0.2224E+02  0.7763E+01         2.86           4 
    0   33    0     2.764   0.1212E+02  0.7563E+01         1.60           4 
    0   34    0     2.682   0.1978E+03  0.1288E+02        15.36           4 
    0   35    0     2.606  -0.2779E+01  0.7588E+01        -0.37           4 
    0   36    0     2.533   0.1071E+02  0.8056E+01         1.33           4 
    0   37    0     2.465  -0.2095E+01  0.1217E+02        -0.17           2 
    0   38    0     2.400   0.1507E+03  0.1611E+02         9.35           2 
    0   39    0     2.338   0.1245E+02  0.8685E+01         1.43           4 
    0   40    0     2.280   0.6260E+02  0.9964E+01         6.28           4 
    0   41    0     2.224   0.8476E+01  0.9296E+01         0.91           4 
    0   42    0     2.171   0.5720E+01  0.8858E+01         0.65           4 
    0   43    0     2.121   0.4710E+01  0.9095E+01         0.52           4 
    2    0    0    39.225   0.2015E+03  0.1073E+02        18.79           2 
    3    0    0    26.150   0.8867E+01  0.1113E+01         7.97           3 
    4    0    0    19.612   0.2500E+04  0.1073E+03        23.30           3 
    5    0    0    15.690   0.4245E+01  0.1424E+01         2.98           4 
    6    0    0    13.075   0.2847E+04  0.1063E+03        26.79           4 
    7    0    0    11.207   0.1558E+03  0.6481E+01        24.04           4 
    8    0    0     9.806   0.4555E+04  0.1702E+03        26.76           4 
    9    0    0     8.717  -0.9747E+00  0.2476E+01        -0.39           4 
   10    0    0     7.845   0.7345E+03  0.2796E+02        26.27           4 
   11    0    0     7.132   0.9090E+01  0.3025E+01         3.01           4 
   12    0    0     6.537   0.1968E+04  0.7396E+02        26.61           4 
   13    0    0     6.035   0.3699E+03  0.1694E+02        21.83           3 
   14    0    0     5.604   0.3691E+04  0.1377E+03        26.81           4 
   15    0    0     5.230   0.2488E+02  0.3828E+01         6.50           4 
   16    0    0     4.903   0.1680E+05  0.6243E+03        26.91           4 
   17    0    0     4.615   0.5482E+03  0.2526E+02        21.70           3 
   18    0    0     4.358   0.1035E+05  0.3854E+03        26.87           4 
   21    0    0     3.736   0.8361E+02  0.8975E+01         9.32           2 
   22    0    0     3.566   0.5996E+03  0.2508E+02        23.91           4 
   23    0    0     3.411   0.9470E+01  0.6142E+01         1.54           4 
   24    0    0     3.269   0.2064E+04  0.7890E+02        26.16           4 
   25    0    0     3.138   0.5273E+02  0.7029E+01         7.50           4 
   26    0    0     3.017   0.2844E+03  0.1419E+02        20.05           4 
   27    0    0     2.906  -0.1195E+01  0.5744E+01        -0.21           4 
   28    0    0     2.802   0.8132E+03  0.3285E+02        24.75           4 
   29    0    0     2.705   0.5007E+01  0.5659E+01         0.88           4 
   30    0    0     2.615   0.4747E+02  0.9319E+01         5.09           3 
   31    0    0     2.531   0.5391E+02  0.6936E+01         7.77           4 
   32    0    0     2.452   0.2120E+02  0.6312E+01         3.36           4 
   33    0    0     2.377   0.2777E+03  0.1524E+02        18.23           3 
   34    0    0     2.307   0.8335E+02  0.8922E+01         9.34           3 
   35    0    0     2.241   0.2904E+01  0.6898E+01         0.42           4 
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
    1169     7.361   0.005  4.1898E+02       6.038      69.4
    1471     5.754   0.008  2.5753E+02       5.551      74.6
    1723     4.879   0.011  3.9597E+02       5.981      33.2
    1923     4.310   0.013  4.3549E+02       6.076      22.3
    2112     3.903   0.016  3.2138E+02       5.773      27.6
    2300     3.592   0.019  2.4596E+02       5.505      30.3
    2434     3.346   0.022  1.6429E+02       5.102      35.3
    2596     3.144   0.025  1.1259E+02       4.724      38.6
    2756     2.975   0.028  8.1831E+01       4.405      40.2
    2861     2.830   0.031  6.1098E+01       4.112      41.1
    3008     2.705   0.034  4.3355E+01       3.769      42.6
    3114     2.595   0.037  3.5385E+01       3.566      41.9
    3223     2.497   0.040  2.7139E+01       3.301      42.1
    3378     2.410   0.043  2.3778E+01       3.169      40.8
    3464     2.331   0.046  2.0529E+01       3.022      39.7
    3554     2.259   0.049  1.6998E+01       2.833      39.2
    3690     2.194   0.052  1.4362E+01       2.665      38.6
    3541     2.135   0.055  1.1854E+01       2.473      38.4


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF  CENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         3<I>**2     15<I>**3    105<I>**4 

     249    11.799        2.012        3.753        6.101
     249     7.361        3.112        9.230       25.003
     250     5.754        1.098        1.379        1.515
     251     4.879        2.943       10.196       30.904
     252     4.310        1.792        3.971        9.374
     245     3.903        1.183        1.528        1.745
     251     3.592        1.467        2.084        2.833
     251     3.346        1.480        2.170        2.888
     246     3.144        1.613        2.481        3.490
     257     2.975        0.902        0.999        1.059
     241     2.830        1.306        1.958        2.968
     258     2.705        1.451        2.400        3.870
     245     2.595        1.788        3.945        8.331
     252     2.497        1.876        3.580        6.265
     254     2.410        1.678        2.694        3.826
     253     2.331        2.515        5.673       10.617
     246     2.259        2.547        5.043        8.966
     253     2.194        1.382        1.928        2.582
     232     2.135        2.091        3.986        7.347
    4735   overall        1.800        3.628        7.348


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF ACENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         2<I>**2      6<I>**3     24<I>**4 

     471    11.799        1.279        2.080        3.684
     920     7.361        1.189        1.802        2.975
    1221     5.754        1.315        1.908        2.831
    1472     4.879        1.262        2.132        4.276
    1671     4.310        1.369        2.650        6.091
    1867     3.903        1.192        1.561        2.227
    2049     3.592        1.075        1.228        1.377
    2183     3.346        1.221        1.759        2.720
    2350     3.144        1.347        2.446        5.294
    2499     2.975        1.422        2.440        4.460
    2620     2.830        1.421        2.741        6.098
    2750     2.705        1.303        2.321        5.158
    2869     2.595        1.502        3.311        8.538
    2971     2.497        1.371        2.484        4.962
    3124     2.410        1.577        4.191       15.849
    3211     2.331        1.467        2.950        6.522
    3308     2.259        1.320        2.280        4.569
    3437     2.194        1.632        3.913       10.889
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
     249     7.361  1.31  1.28  1.25  1.19  1.16  1.18  1.16  1.14  1.13  1.09
     250     5.754  0.95  1.15  1.19  1.16  1.16  1.15  1.17  1.17  1.16  1.14
     251     4.879  0.99  1.03  1.06  1.09  1.10  1.09  1.11  1.12  1.09  1.08
     252     4.310  1.04  1.07  1.07  1.03  1.02  1.02  1.01  1.00  1.03  1.02
     245     3.903  1.07  1.19  1.18  1.14  1.12  1.13  1.13  1.12  1.11  1.11
     251     3.592  0.90  0.91  1.00  1.00  0.99  1.06  1.08  1.07  1.05  1.06
     251     3.346  0.91  1.04  1.06  1.12  1.06  1.07  1.06  1.03  1.04  1.02
     246     3.144  0.92  1.01  1.06  1.06  1.07  1.04  1.04  1.03  1.03  1.03
     257     2.975  0.71  0.89  1.08  1.09  1.14  1.17  1.15  1.13  1.11  1.10
     241     2.830  0.87  0.94  1.07  1.07  1.12  1.12  1.09  1.07  1.06  1.08
     258     2.705  0.72  0.80  0.86  1.00  1.09  1.11  1.11  1.09  1.09  1.08
     245     2.595  0.76  0.84  0.91  0.96  0.97  1.00  1.03  1.05  1.05  1.06
     252     2.497  0.97  1.10  1.07  1.07  1.11  1.13  1.12  1.12  1.11  1.10
     254     2.410  1.00  0.96  0.95  1.05  1.04  1.09  1.07  1.05  1.05  1.04
     253     2.331  0.94  0.91  0.91  0.98  1.08  1.11  1.13  1.09  1.08  1.09
     246     2.259  0.93  0.87  0.89  0.93  0.93  0.97  0.98  0.98  1.00  0.99
     253     2.194  1.10  0.99  0.97  0.94  0.96  0.99  1.00  0.97  1.03  1.04
     232     2.135  0.99  0.85  0.84  0.85  0.84  0.91  0.94  0.94  0.95  0.97
    4735   overall  0.98  1.01  1.04  1.05  1.06  1.08  1.08  1.07  1.07  1.06


 Na(Z)obs/Na(Z)exp versus resolution and Z (0.1-1.0)
      #      RES     0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   0.9   1.0

     471    11.799  1.63  1.56  1.38  1.35  1.27  1.22  1.17  1.13  1.10  1.08
     920     7.361  1.39  1.48  1.33  1.31  1.24  1.21  1.16  1.15  1.14  1.12
    1221     5.754  1.12  1.14  1.12  1.18  1.17  1.16  1.12  1.09  1.07  1.05
    1472     4.879  1.25  1.24  1.24  1.21  1.20  1.16  1.14  1.12  1.10  1.08
    1671     4.310  1.16  1.21  1.18  1.21  1.21  1.18  1.16  1.13  1.12  1.10
    1867     3.903  0.99  1.11  1.15  1.13  1.12  1.08  1.09  1.08  1.08  1.06
    2049     3.592  0.87  1.05  1.08  1.10  1.08  1.08  1.08  1.05  1.06  1.06
    2183     3.346  0.96  1.15  1.19  1.17  1.15  1.12  1.11  1.08  1.07  1.07
    2350     3.144  0.99  1.13  1.18  1.20  1.18  1.19  1.17  1.15  1.13  1.10
    2499     2.975  1.11  1.25  1.23  1.19  1.16  1.17  1.16  1.14  1.11  1.10
    2620     2.830  1.07  1.19  1.19  1.19  1.16  1.15  1.13  1.12  1.11  1.10
    2750     2.705  0.97  1.04  1.07  1.10  1.11  1.11  1.10  1.09  1.08  1.07
    2869     2.595  1.27  1.15  1.20  1.17  1.16  1.14  1.13  1.12  1.12  1.10
    2971     2.497  1.18  1.04  1.09  1.10  1.11  1.11  1.10  1.09  1.09  1.09
    3124     2.410  1.41  1.17  1.14  1.14  1.14  1.13  1.12  1.12  1.11  1.10
    3211     2.331  1.32  1.17  1.13  1.14  1.16  1.14  1.13  1.12  1.10  1.10
    3308     2.259  1.48  1.14  1.11  1.10  1.10  1.11  1.10  1.10  1.08  1.08
    3437     2.194  1.77  1.26  1.14  1.09  1.08  1.07  1.08  1.07  1.07  1.06
    3309     2.135  1.82  1.24  1.10  1.04  1.02  1.02  1.03  1.04  1.04  1.04
   44302   overall  1.27  1.17  1.15  1.14  1.13  1.12  1.11  1.10  1.09  1.08


 List of    117 reflections *NOT* obeying Wilson distribution (Z> 10.0)

   h    k    l     RES      Z     Intensity    Sigma

   23   26    9    2.40   28.65  0.6813E+03  0.2005E+02 "alien"
    9   33    1    2.63   18.15  0.6424E+03  0.1713E+02 "alien"
   24   18    3    2.74   18.02  0.7815E+03  0.2455E+02 "alien"
   35    7    3    2.21   17.38  0.2496E+03  0.8507E+01 "alien"
   24   30    2    2.22   17.31  0.2486E+03  0.1272E+02 "alien"
   24   25    6    2.42   17.02  0.4047E+03  0.1731E+02 "alien"
   23   28   17    2.23   16.88  0.2869E+03  0.9816E+01 "alien"
   33    4    1    2.36   16.59  0.3406E+03  0.1312E+02 "alien"
   12   24   10    3.16   16.45  0.1852E+04  0.3620E+02 "alien"
   20   26    6    2.59   16.24  0.5748E+03  0.1383E+02 "alien"
   35    6    2    2.22   16.17  0.2322E+03  0.1188E+02 "alien"
   15   30    5    2.61   15.97  0.5650E+03  0.1352E+02 "alien"
   29   16    6    2.42   15.94  0.3789E+03  0.1506E+02 "alien"
   27    1    5    2.88   15.75  0.9624E+03  0.2043E+02 "alien"
   35    5    1    2.22   15.64  0.2246E+03  0.8853E+01 "alien"
   31   20   10    2.17   15.59  0.2238E+03  0.1551E+02 "alien"
   27    4    5    2.86   15.43  0.9428E+03  0.2350E+02 "alien"
   36    6    2    2.16   15.36  0.1820E+03  0.8258E+01 "alien"
   16   10    3    4.29   15.25  0.6642E+04  0.1571E+03 "alien"
   34   14    3    2.17   15.23  0.2187E+03  0.2352E+02 "alien"
   23   26   11    2.38   15.21  0.3616E+03  0.1049E+02 "alien"
   32   19    5    2.17   14.99  0.2152E+03  0.1170E+02 "alien"
   13   13    2    4.56   14.98  0.6524E+04  0.1257E+03 "alien"
   12   14    1    4.61   14.84  0.5877E+04  0.1096E+03 "alien"
   26   24    5    2.35   14.76  0.3031E+03  0.1372E+02 "alien"
   35   11    1    2.16   14.75  0.1749E+03  0.1022E+02 "alien"
   23   15    4    2.96   14.70  0.1203E+04  0.3608E+02 "alien"
   34   14    5    2.17   14.53  0.2087E+03  0.1529E+02 "alien"
   31    1    1    2.53   14.41  0.3910E+03  0.9887E+01 "alien"
    1   28    5    3.22   14.24  0.1604E+04  0.3223E+02 "alien"
   35   14    4    2.11   14.23  0.1686E+03  0.1142E+02 "alien"
   29   23    8    2.21   14.07  0.2021E+03  0.1139E+02 "alien"
   27    2    7    2.86   14.00  0.8554E+03  0.1870E+02 "alien"
   28   17    1    2.48   13.97  0.3792E+03  0.1535E+02 "alien"
   24    6    2    3.19   13.97  0.1573E+04  0.3026E+02 "alien"
    9   39    5    2.25   13.96  0.2373E+03  0.8948E+01 "alien"
   25   18    4    2.66   13.80  0.5982E+03  0.2013E+02 "alien"
   20   26    2    2.61   13.74  0.4861E+03  0.1117E+02 "alien"
   24   32    2    2.15   13.66  0.1619E+03  0.5611E+01 "alien"
   26   14    1    2.74   13.53  0.5866E+03  0.1907E+02 "alien"
    2   20   13    4.04   13.34  0.4289E+04  0.9321E+02 "alien"
   15   30    7    2.60   13.27  0.4696E+03  0.1212E+02 "alien"
   11   13   12    4.45   13.12  0.5715E+04  0.1144E+03 "alien"
   11   13    6    4.84   13.03  0.5160E+04  0.9622E+02 "alien"
   30   16    1    2.38   12.92  0.3073E+03  0.1728E+02 "alien"
   28   19    7    2.39   12.87  0.3060E+03  0.1515E+02 "alien"
   19   23    1    2.86   12.69  0.7754E+03  0.1640E+02 "alien"
   20   27   12    2.48   12.67  0.3439E+03  0.1035E+02 "alien"
   20   31   20    2.18   12.63  0.1814E+03  0.9348E+01 "alien"
   35    5    7    2.21   12.58  0.1806E+03  0.6612E+01 "alien"
   23    3    1    3.39   12.50  0.2053E+04  0.3893E+02 "alien"
   14   30   14    2.54   12.46  0.4408E+03  0.1169E+02 "alien"
   22   22    9    2.65   12.42  0.4394E+03  0.1916E+02 "alien"
   31   13    6    2.36   12.36  0.2538E+03  0.1187E+02 "alien"
   32   10    1    2.37   12.12  0.2489E+03  0.1111E+02 "alien"
   18   28    4    2.60   12.07  0.4272E+03  0.1401E+02 "alien"
   36    4    4    2.16   12.07  0.1431E+03  0.6764E+01 "alien"
   26   28    4    2.21   12.05  0.1730E+03  0.1108E+02 "alien"
   26   14    3    2.73   11.93  0.5172E+03  0.1760E+02 "alien"
   28   27    8    2.13   11.90  0.1411E+03  0.9727E+01 "alien"
   31   21    6    2.17   11.87  0.1705E+03  0.1059E+02 "alien"
    1    1   13    8.89   11.79  0.4940E+04  0.9203E+02 "alien"
    1   20    3    4.52   11.71  0.5101E+04  0.9545E+02 "alien"
    6    3    8    9.28   11.61  0.7229E+04  0.1343E+03 "alien"
   32   17    1    2.23   11.61  0.1973E+03  0.1108E+02 "alien"
   25    9    3    2.99   11.52  0.9428E+03  0.2343E+02 "alien"
    7   27    1    3.23   11.52  0.1297E+04  0.2600E+02 "alien"
   24   16    3    2.83   11.46  0.7000E+03  0.2438E+02 "alien"
   11   35   17    2.31   11.23  0.2306E+03  0.8438E+01 "alien"
    0   12    3    7.46   11.23  0.1027E+05  0.2698E+03 "alien"
   31    3    7    2.49   11.21  0.3043E+03  0.8712E+01 "alien"
   20   36    8    2.11   11.21  0.1328E+03  0.8748E+01 "alien"
   28   24   12    2.20   11.18  0.1606E+03  0.1168E+02 "alien"
   23   17    4    2.86   11.18  0.6829E+03  0.2529E+02 "alien"
   24   27   12    2.28   11.17  0.1900E+03  0.6623E+01 "alien"
   32   18    1    2.21   11.13  0.1598E+03  0.1035E+02 "alien"
   33    5    3    2.35   11.12  0.2284E+03  0.7376E+01 "alien"
   26   24    3    2.36   11.11  0.2282E+03  0.1188E+02 "alien"
    1   36    6    2.51   11.10  0.3014E+03  0.1079E+02 "alien"
   16    0    2    4.89   11.09  0.9502E+04  0.2499E+03 "alien"
    4   25   21    3.01   11.07  0.9062E+03  0.2036E+02 "alien"
   16   30    2    2.58   11.04  0.3906E+03  0.9874E+01 "alien"
    8   35   16    2.38   10.97  0.2609E+03  0.9776E+01 "alien"
   28   17    3    2.48   10.96  0.2974E+03  0.1318E+02 "alien"
   35    5    9    2.19   10.93  0.1569E+03  0.6214E+01 "alien"
   16   30   22    2.32   10.90  0.2238E+03  0.8256E+01 "alien"
   13    2    5    5.80   10.79  0.2780E+04  0.5197E+02 "alien"
   23   19   20    2.51   10.79  0.2928E+03  0.9464E+01 "alien"
   14   35    9    2.32   10.74  0.2205E+03  0.9860E+01 "alien"
   28   26    4    2.18   10.72  0.1540E+03  0.1074E+02 "alien"
   26   23    4    2.39   10.70  0.2544E+03  0.1237E+02 "alien"
   14   29   12    2.64   10.68  0.3778E+03  0.1082E+02 "alien"
   22   15    1    3.07   10.67  0.1202E+04  0.3466E+02 "alien"
   13   13    6    4.45   10.67  0.4647E+04  0.8691E+02 "alien"
   28   27    6    2.14   10.66  0.1264E+03  0.1033E+02 "alien"
   11   35   15    2.33   10.66  0.2188E+03  0.8743E+01 "alien"
   13   13    0    4.57   10.64  0.8852E+04  0.2491E+03 "alien"
   12    8    1    5.66   10.49  0.2701E+04  0.7188E+02 "alien"
    1   38    4    2.39   10.46  0.2487E+03  0.1086E+02 "alien"
   30   17    1    2.35   10.45  0.2146E+03  0.1616E+02 "alien"
    7   29    1    3.03   10.45  0.8554E+03  0.1836E+02 "alien"
   30   12    9    2.43   10.36  0.2463E+03  0.9021E+01 "alien"
   16    0    0    4.90   10.35  0.1689E+05  0.6278E+03 "alien"
   14   35   11    2.31   10.35  0.2124E+03  0.9765E+01 "alien"
   13   14    0    4.43   10.34  0.9287E+04  0.2443E+03 "alien"
   21   25   13    2.51   10.33  0.2805E+03  0.8073E+01 "alien"
    4   39   13    2.25   10.30  0.1750E+03  0.8646E+01 "alien"
   23   28    7    2.33   10.19  0.2092E+03  0.6488E+01 "alien"
   15   37    9    2.20   10.11  0.1451E+03  0.9390E+01 "alien"
   27   13    8    2.64   10.11  0.3576E+03  0.1056E+02 "alien"
   29   13   14    2.42   10.10  0.2402E+03  0.8796E+01 "alien"
   30   17   15    2.25   10.09  0.1715E+03  0.8202E+01 "alien"
    0   12    1    7.58   10.09  0.8572E+04  0.2254E+03 "alien"
   16    2    4    4.81   10.05  0.3981E+04  0.9394E+02 "alien"
   22    4    8    3.42   10.05  0.1651E+04  0.3392E+02 "alien"
    5   30   17    2.74   10.04  0.4354E+03  0.1156E+02 "alien"
   30   17    9    2.31   10.01  0.2055E+03  0.1419E+02 "alien"


 List of    117 reflections *NOT* obeying Wilson distribution (sorted by resolution)
 Ice rings could occur at (Angstrom):
 3.897,3.669,3.441, 2.671,2.249,2.072, 1.948,1.918,1.883,1.721

   h    k    l     RES      Z     Intensity    Sigma

   20   36    8    2.11   11.21  0.1328E+03  0.8748E+01
   35   14    4    2.11   14.23  0.1686E+03  0.1142E+02
   28   27    8    2.13   11.90  0.1411E+03  0.9727E+01
   28   27    6    2.14   10.66  0.1264E+03  0.1033E+02
   24   32    2    2.15   13.66  0.1619E+03  0.5611E+01
   36    6    2    2.16   15.36  0.1820E+03  0.8258E+01
   36    4    4    2.16   12.07  0.1431E+03  0.6764E+01
   35   11    1    2.16   14.75  0.1749E+03  0.1022E+02
   34   14    5    2.17   14.53  0.2087E+03  0.1529E+02
   34   14    3    2.17   15.23  0.2187E+03  0.2352E+02
   31   21    6    2.17   11.87  0.1705E+03  0.1059E+02
   32   19    5    2.17   14.99  0.2152E+03  0.1170E+02
   31   20   10    2.17   15.59  0.2238E+03  0.1551E+02
   20   31   20    2.18   12.63  0.1814E+03  0.9348E+01
   28   26    4    2.18   10.72  0.1540E+03  0.1074E+02
   35    5    9    2.19   10.93  0.1569E+03  0.6214E+01
   28   24   12    2.20   11.18  0.1606E+03  0.1168E+02
   15   37    9    2.20   10.11  0.1451E+03  0.9390E+01
   35    5    7    2.21   12.58  0.1806E+03  0.6612E+01
   35    7    3    2.21   17.38  0.2496E+03  0.8507E+01
   32   18    1    2.21   11.13  0.1598E+03  0.1035E+02
   26   28    4    2.21   12.05  0.1730E+03  0.1108E+02
   29   23    8    2.21   14.07  0.2021E+03  0.1139E+02
   35    6    2    2.22   16.17  0.2322E+03  0.1188E+02
   35    5    1    2.22   15.64  0.2246E+03  0.8853E+01
   24   30    2    2.22   17.31  0.2486E+03  0.1272E+02
   23   28   17    2.23   16.88  0.2869E+03  0.9816E+01
   32   17    1    2.23   11.61  0.1973E+03  0.1108E+02
    9   39    5    2.25   13.96  0.2373E+03  0.8948E+01
    4   39   13    2.25   10.30  0.1750E+03  0.8646E+01
   30   17   15    2.25   10.09  0.1715E+03  0.8202E+01
   24   27   12    2.28   11.17  0.1900E+03  0.6623E+01
   11   35   17    2.31   11.23  0.2306E+03  0.8438E+01
   14   35   11    2.31   10.35  0.2124E+03  0.9765E+01
   30   17    9    2.31   10.01  0.2055E+03  0.1419E+02
   16   30   22    2.32   10.90  0.2238E+03  0.8256E+01
   14   35    9    2.32   10.74  0.2205E+03  0.9860E+01
   23   28    7    2.33   10.19  0.2092E+03  0.6488E+01
   11   35   15    2.33   10.66  0.2188E+03  0.8743E+01
   30   17    1    2.35   10.45  0.2146E+03  0.1616E+02
   26   24    5    2.35   14.76  0.3031E+03  0.1372E+02
   33    5    3    2.35   11.12  0.2284E+03  0.7376E+01
   26   24    3    2.36   11.11  0.2282E+03  0.1188E+02
   31   13    6    2.36   12.36  0.2538E+03  0.1187E+02
   33    4    1    2.36   16.59  0.3406E+03  0.1312E+02
   32   10    1    2.37   12.12  0.2489E+03  0.1111E+02
   30   16    1    2.38   12.92  0.3073E+03  0.1728E+02
    8   35   16    2.38   10.97  0.2609E+03  0.9776E+01
   23   26   11    2.38   15.21  0.3616E+03  0.1049E+02
    1   38    4    2.39   10.46  0.2487E+03  0.1086E+02
   26   23    4    2.39   10.70  0.2544E+03  0.1237E+02
   28   19    7    2.39   12.87  0.3060E+03  0.1515E+02
   23   26    9    2.40   28.65  0.6813E+03  0.2005E+02
   24   25    6    2.42   17.02  0.4047E+03  0.1731E+02
   29   13   14    2.42   10.10  0.2402E+03  0.8796E+01
   29   16    6    2.42   15.94  0.3789E+03  0.1506E+02
   30   12    9    2.43   10.36  0.2463E+03  0.9021E+01
   20   27   12    2.48   12.67  0.3439E+03  0.1035E+02
   28   17    3    2.48   10.96  0.2974E+03  0.1318E+02
   28   17    1    2.48   13.97  0.3792E+03  0.1535E+02
   31    3    7    2.49   11.21  0.3043E+03  0.8712E+01
   21   25   13    2.51   10.33  0.2805E+03  0.8073E+01
   23   19   20    2.51   10.79  0.2928E+03  0.9464E+01
    1   36    6    2.51   11.10  0.3014E+03  0.1079E+02
   31    1    1    2.53   14.41  0.3910E+03  0.9887E+01
   14   30   14    2.54   12.46  0.4408E+03  0.1169E+02
   16   30    2    2.58   11.04  0.3906E+03  0.9874E+01
   20   26    6    2.59   16.24  0.5748E+03  0.1383E+02
   15   30    7    2.60   13.27  0.4696E+03  0.1212E+02
   18   28    4    2.60   12.07  0.4272E+03  0.1401E+02
   15   30    5    2.61   15.97  0.5650E+03  0.1352E+02
   20   26    2    2.61   13.74  0.4861E+03  0.1117E+02
    9   33    1    2.63   18.15  0.6424E+03  0.1713E+02
   14   29   12    2.64   10.68  0.3778E+03  0.1082E+02
   27   13    8    2.64   10.11  0.3576E+03  0.1056E+02
   22   22    9    2.65   12.42  0.4394E+03  0.1916E+02
   25   18    4    2.66   13.80  0.5982E+03  0.2013E+02
   26   14    3    2.73   11.93  0.5172E+03  0.1760E+02
   26   14    1    2.74   13.53  0.5866E+03  0.1907E+02
    5   30   17    2.74   10.04  0.4354E+03  0.1156E+02
   24   18    3    2.74   18.02  0.7815E+03  0.2455E+02
   24   16    3    2.83   11.46  0.7000E+03  0.2438E+02
   27    2    7    2.86   14.00  0.8554E+03  0.1870E+02
   19   23    1    2.86   12.69  0.7754E+03  0.1640E+02
   27    4    5    2.86   15.43  0.9428E+03  0.2350E+02
   23   17    4    2.86   11.18  0.6829E+03  0.2529E+02
   27    1    5    2.88   15.75  0.9624E+03  0.2043E+02
   23   15    4    2.96   14.70  0.1203E+04  0.3608E+02
   25    9    3    2.99   11.52  0.9428E+03  0.2343E+02
    4   25   21    3.01   11.07  0.9062E+03  0.2036E+02
    7   29    1    3.03   10.45  0.8554E+03  0.1836E+02
   22   15    1    3.07   10.67  0.1202E+04  0.3466E+02
   12   24   10    3.16   16.45  0.1852E+04  0.3620E+02
   24    6    2    3.19   13.97  0.1573E+04  0.3026E+02
    1   28    5    3.22   14.24  0.1604E+04  0.3223E+02
    7   27    1    3.23   11.52  0.1297E+04  0.2600E+02
   23    3    1    3.39   12.50  0.2053E+04  0.3893E+02
   22    4    8    3.42   10.05  0.1651E+04  0.3392E+02
    2   20   13    4.04   13.34  0.4289E+04  0.9321E+02
   16   10    3    4.29   15.25  0.6642E+04  0.1571E+03
   13   14    0    4.43   10.34  0.9287E+04  0.2443E+03
   11   13   12    4.45   13.12  0.5715E+04  0.1144E+03
   13   13    6    4.45   10.67  0.4647E+04  0.8691E+02
    1   20    3    4.52   11.71  0.5101E+04  0.9545E+02
   13   13    2    4.56   14.98  0.6524E+04  0.1257E+03
   13   13    0    4.57   10.64  0.8852E+04  0.2491E+03
   12   14    1    4.61   14.84  0.5877E+04  0.1096E+03
   16    2    4    4.81   10.05  0.3981E+04  0.9394E+02
   11   13    6    4.84   13.03  0.5160E+04  0.9622E+02
   16    0    2    4.89   11.09  0.9502E+04  0.2499E+03
   16    0    0    4.90   10.35  0.1689E+05  0.6278E+03
   12    8    1    5.66   10.49  0.2701E+04  0.7188E+02
   13    2    5    5.80   10.79  0.2780E+04  0.5197E+02
    0   12    3    7.46   11.23  0.1027E+05  0.2698E+03
    0   12    1    7.58   10.09  0.8572E+04  0.2254E+03
    1    1   13    8.89   11.79  0.4940E+04  0.9203E+02
    6    3    8    9.28   11.61  0.7229E+04  0.1343E+03

 NUMBER OF UNIQUE ALIEN REFLECTIONS WITH A Z-SCORE ABOVE LIMIT         1
 (ALIENS ABOVE LIMIT (REJECT_ALIEN=      20.0) ARE MARKED INVALID)

 NUMBER OF REFLECTION RECORDS ON OUTPUT FILE "XDS_ASCII.HKL"      643779
 NUMBER OF ACCEPTED OBSERVATIONS (INCLUDING SYSTEMATIC ABSENCES)  632934
 NUMBER OF REJECTED MISFITS & ALIENS (marked by -1*SIGMA(IOBS))    10845


 cpu time used                223.6 sec
 elapsed wall-clock time       25.8 sec
