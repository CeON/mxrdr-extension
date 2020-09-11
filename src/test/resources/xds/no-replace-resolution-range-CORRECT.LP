
 ***** CORRECT ***** (VERSION Jan 31, 2020  BUILT=20200417)   2-Sep-2020

 INPUT PARAMETER VALUES
 ----------------------
 SPACE_GROUP_NUMBER=    1 as used in the INTEGRATE step
 UNIT_CELL_CONSTANTS=   107.427   114.435   138.534  84.758  84.927  59.815 as used by INTEGRATE
 FRIEDEL'S_LAW=FALSE
 PROFILE_FITTING= TRUE 
 OVERLOAD=     65000     MINPK=  75.00000    WFAC1=   1.0
 INCLUDE_RESOLUTION_RANGE=    50.000     0.000
 NAME_TEMPLATE_OF_DATA_FRAMES=img/113252_102_M1_B8_data.0???.img SMV
 DATA_RANGE=       1     180
 ROTATION_AXIS=  0.998186  0.051066  0.031892
 OSCILLATION_RANGE=  1.000000
 STARTING_ANGLE=     0.000     STARTING_FRAME=       1
 X-RAY_WAVELENGTH=  0.979322
 INCIDENT_BEAM_DIRECTION= -0.002291 -0.004305  0.999988
 FRACTION_OF_POLARIZATION=   0.980
 POLARIZATION_PLANE_NORMAL=  0.000000  1.000000  0.000000
 AIR=   0.00032
 DETECTOR=ADSC
 SILICON=  3.704902 SENSOR_THICKNESS=  0.010000
 NUMBER OF DETECTOR SEGMENTS   1
 NX=  3072  NY=  3072    QX=  0.102400  QY=  0.102400
 ORGX=   1541.00  ORGY=   1507.60
 DETECTOR_DISTANCE=   300.000
 DIRECTION_OF_DETECTOR_X-AXIS=   1.00000   0.00000   0.00000
 DIRECTION_OF_DETECTOR_Y-AXIS=   0.00000   1.00000   0.00000
 BEAM_DIVERGENCE_E.S.D.=   0.064
 REFLECTING_RANGE_E.S.D.=   0.598
 MINIMUM_ZETA=     0.050
 MAXIMUM_ERROR_OF_SPOT_POSITION=   3.0
 MAXIMUM_ERROR_OF_SPINDLE_POSITION=   2.0
 MINIMUM_I/SIGMA=   3.0
 REFLECTIONS/CORRECTION_FACTOR=      50
 SNRC=  0.10 I/sigma for use in scaling factor determination
 STRICT_ABSORPTION_CORRECTION=FALSE 
 CORRECTIONS= DECAY MODULATION ABSORPTION                      
 REFERENCE_DATA_SET=                                                  
 FIT_B-FACTOR_TO_REFERENCE_DATA_SET=FALSE 

  819911 REFLECTIONS ON FILE "INTEGRATE.HKL"
       0 CORRUPTED REFLECTION RECORDS (IGNORED)
       0 MATCHING REFLECTIONS SPECIFIED IN FILE "FILTER.HKL"
       0 REFLECTIONS INCOMPLETE OR OUTSIDE IMAGE RANGE       1 ...     180
     415 OVERLOADED REFLECTIONS (IGNORED)
    1435 REFLECTIONS OUTSIDE ACCEPTED RESOLUTION RANGES
                OR TOO CLOSE TO ROTATION AXIS (IGNORED)
  818061 REFLECTIONS ACCEPTED



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
 NUMBER OF ACCEPTED REFLECTIONS FOR CALCULATING Rmeas            39863
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

 *  44        aP          0.0     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
 *  31        aP          5.7     107.4  110.8  138.5  90.5  84.9  63.2   -1  0  0  0 -1  1  0  0  0  0 -1  0
    29        mC         57.7     107.4  198.0  138.5  93.3  95.1  87.8   -1  0  0  0  1 -2  0  0  0  0  1  0
    34        mP         62.8     107.4  138.5  110.8  90.5 116.8  95.1    1  0  0  0  0  0 -1  0 -1  1  0  0
    39        mC         71.2     192.3  110.8  138.5  90.5  96.0  85.8   -1 -1  0  0  1 -1  0  0  0  0  1  0
    38        oC         80.5     107.4  198.0  138.5  86.7  95.1  92.2    1  0  0  0  1 -2  0  0  0  0 -1  0
    14        mC         83.1     114.4  185.8  138.5  87.4  95.2  88.0    0 -1  0  0  2 -1  0  0  0  0  1  0
    13        oC         94.5     114.4  185.8  138.5  87.4  95.2  88.0    0 -1  0  0  2 -1  0  0  0  0  1  0
    10        mC        100.3     185.8  114.4  138.5  95.2  92.6  92.0   -2  1  0  0  0 -1  0  0  0  0  1  0
    12        hP        112.3     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
    33        mP        237.9     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
    35        mP        269.7     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
    32        oP        295.0     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
    28        mC        299.7     107.4  288.2  114.4  95.7 120.2  73.3   -1  0  0  0 -1  0  2  0  0  1  0  0
    11        tP        326.8     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
    30        mC        331.5     107.4  288.2  114.4  95.7 120.2  73.3   -1  0  0  0 -1  0  2  0  0  1  0  0
    27        mC        388.6     198.0  107.4  171.4  74.7 122.9  92.2   -1  2  0  0 -1  0  0  0  0 -1  1  0
    37        mC        425.2     288.2  107.4  110.8 116.8 100.1  73.3   -1  0  2  0 -1  0  0  0  1 -1  0  0
    36        oC        430.9     107.4  288.2  110.8  79.9 116.8 106.7   -1  0  0  0  1  0 -2  0  1 -1  0  0
    26        oF        450.8     107.4  198.0  288.2  87.6 106.7  87.8    1  0  0  0 -1  2  0  0 -1  0  2  0
    41        mC        457.0     288.2  107.4  110.8 116.8 100.1  73.3   -1  0  2  0 -1  0  0  0  1 -1  0  0
    40        oC        462.7     107.4  288.2  110.8  79.9 116.8 106.7   -1  0  0  0  1  0 -2  0  1 -1  0  0
    25        mC        474.9     176.6  178.1  107.4  77.8 110.6  77.3    1 -1  1  0 -1  1  1  0 -1  0  0  0
    20        mC        480.6     176.6  178.1  107.4 102.2 110.6 102.7   -1  1 -1  0 -1  1  1  0  1  0  0  0
     4        hR        558.0     185.8  182.7  171.4 114.3  86.5 122.2   -2  1  0  0  1  0  1  0  0  1 -1  0
     3        cP        563.7     107.4  110.8  138.5  90.5  95.1 116.8   -1  0  0  0  1 -1  0  0  0  0  1  0
     2        hR        575.1     114.4  167.6  236.8  93.1  91.5 104.3    0 -1  0  0  1  0 -1  0  2 -1  1  0
    23        oC        589.0     176.6  178.1  107.4  77.8 110.6  77.3    1 -1  1  0 -1  1  1  0 -1  0  0  0
    21        tP        594.7     110.8  138.5  107.4  95.1 116.8  90.5    1 -1  0  0  0  0  1  0 -1  0  0  0
    17        mC        627.3     185.8  114.4  167.6  75.7 120.3  88.0   -2  1  0  0  0  1  0  0  1  0 -1  0
     9        hR        643.2     107.4  110.8  439.5  91.4 107.0 116.8    1  0  0  0 -1  1  0  0 -1 -1  3  0
    19        oI        652.7     107.4  171.4  178.1  69.0  77.8  74.7   -1  0  0  0  0 -1  1  0 -1  1  1  0
    16        oF        678.7     114.4  185.8  289.9  93.3 107.9  88.0    0  1  0  0 -2  1  0  0  0 -1  2  0
    42        oI        685.4     107.4  110.8  289.9 101.9  96.5 116.8    1  0  0  0 -1  1  0  0  0 -1  2  0
    15        tI        701.2     107.4  110.8  289.9  78.1  83.5 116.8   -1  0  0  0  1 -1  0  0  0 -1  2  0
    18        tI        715.0     171.4  178.1  107.4  77.8 105.3 111.0    0 -1  1  0  1 -1 -1  0  1  0  0  0
     5        cI        718.9     167.6  114.4  176.6  74.3  65.3  75.7   -1  0  1  0  0 -1  0  0  1 -1  1  0
     1        cF        770.8     176.6  225.1  178.1 112.0  77.3 116.7    1 -1  1  0  1  1 -1  0 -1  1  1  0
    22        hP        794.2     107.4  138.5  110.8  90.5 116.8  95.1    1  0  0  0  0  0 -1  0 -1  1  0  0
    43        mI        822.1     107.4  288.2  114.4  95.7 120.2  73.3   -1  0  0  0 -1  0  2  0  0  1  0  0
     8        oI        927.1     114.4  167.6  176.6  65.3  74.3  75.7    0  1  0  0  1  0 -1  0 -1  1 -1  0
     6        tI        978.4     176.6  167.6  114.4  75.7  74.3  65.3    1 -1  1  0 -1  0  1  0  0 -1  0  0
     7        tI        999.0     167.6  114.4  176.6  74.3  65.3  75.7   -1  0  1  0  0 -1  0  0  1 -1  1  0
    24        hR        999.0     235.0  178.1  107.4  77.8  91.1  92.0    1 -2  1  0 -1  1  1  0 -1  0  0  0

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

       1     107.4  110.8  138.5  90.5  84.9  63.2   21113   125.2    18750    31 aP
   *   1     107.4  110.8  138.5  90.5  95.1 116.8   21113   125.2    18750    44 aP
 

 ************ SELECTED SPACE GROUP AND UNIT CELL FOR THIS DATA SET ************

 SPACE_GROUP_NUMBER=    1
 UNIT_CELL_CONSTANTS=   107.43   110.79   138.53  90.492  95.075 116.765
 UNIT_CELL_A-AXIS=    49.813    -0.369    95.179
 UNIT_CELL_B-AXIS=   -91.482    61.953    -8.194
 UNIT_CELL_C-AXIS=   -76.908  -112.257    25.983
 REFLECTIONS H,K,L FROM "INTEGRATE.HKL" WILL BE REINDEXED BY
 H' = ( -1*H +  0*K +  0*L)/ 1 +  0
 K' = (  1*H + -1*K +  0*L)/ 1 +  0
 L' = (  0*H +  0*K +  1*L)/ 1 +  0

 *****  1 EQUIVALENT POSITIONS IN SPACE GROUP #  1 *****

    If x',y',z' is an equivalent position to x,y,z, then
        x'=x*ML(1)+y*ML( 2)+z*ML( 3)+ML( 4)/12.0
        y'=x*ML(5)+y*ML( 6)+z*ML( 7)+ML( 8)/12.0
        z'=x*ML(9)+y*ML(10)+z*ML(11)+ML(12)/12.0

    #    1  2  3  4    5  6  7  8    9 10 11 12 
    1    1  0  0  0    0  1  0  0    0  0  1  0

 ******************************************************************************
      MEAN DISCREPANCIES BETWEEN OBSERVED AND CALCULATED SPOT LOCATIONS
 ******************************************************************************

 The discrepancies in X- and Y-coordinates of the spots are depicted in the
 two images DX-CORRECTIONS.cbf and DY-CORRECTIONS.cbf for inspection with
 the XDS-Viewer.



 ******************************************************************************
  REFINEMENT OF DIFFRACTION PARAMETERS USING ALL IMAGES
 ******************************************************************************


 REFINED VALUES OF DIFFRACTION PARAMETERS DERIVED FROM      9919 INDEXED SPOTS
 REFINED PARAMETERS:   POSITION BEAM AXIS ORIENTATION CELL
 STANDARD DEVIATION OF SPOT    POSITION (PIXELS)     2.22
 STANDARD DEVIATION OF SPINDLE POSITION (DEGREES)    0.90
 SPACE GROUP NUMBER      1
 UNIT CELL PARAMETERS    107.615   110.949   138.700  90.488  95.085 116.743
 E.S.D. OF CELL PARAMETERS  3.0E-01 2.6E-01 3.4E-01 1.2E-02 2.5E-02 2.1E-02
 REC. CELL PARAMETERS   0.010462  0.010108  0.007249  86.880  84.056  63.093
 COORDINATES OF UNIT CELL A-AXIS    51.550    -1.728    94.448
 COORDINATES OF UNIT CELL B-AXIS   -90.091    64.425    -6.534
 COORDINATES OF UNIT CELL C-AXIS   -79.445  -110.359    27.333
 CRYSTAL MOSAICITY (DEGREES)     0.598
 LAB COORDINATES OF ROTATION AXIS  0.999086  0.026777  0.033307
 DIRECT BEAM COORDINATES (REC. ANGSTROEM)  -0.004966 -0.007717  1.021073
 DETECTOR COORDINATES (PIXELS) OF DIRECT BEAM    1531.88   1495.52
 DETECTOR ORIGIN (PIXELS) AT                     1546.15   1517.69
 CRYSTAL TO DETECTOR DISTANCE (mm)       300.29
 LAB COORDINATES OF DETECTOR X-AXIS  1.000000  0.000000  0.000000
 LAB COORDINATES OF DETECTOR Y-AXIS  0.000000  1.000000  0.000000


 THE DATA COLLECTION STATISTICS REPORTED BELOW ASSUMES:
 SPACE_GROUP_NUMBER=    1
 UNIT_CELL_CONSTANTS=   107.61   110.95   138.70  90.488  95.085 116.743


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
 -0.05  0.05     80825        117.236       0.961
  0.05  0.15     81127        113.108       0.996
  0.15  0.25     81771        112.008       1.006
  0.25  0.35     82071        108.555       1.038
  0.35  0.45     82406        109.872       1.025
  0.45  0.55     83005        107.258       1.050
  0.55  0.65     82690        109.365       1.030
  0.65  0.75     81674        111.400       1.011
  0.75  0.85     81520        115.504       0.975
  0.85  0.95     80972        122.674       0.918
 
 -0.05  0.95    818061        112.666       1.000
 

 ******************************************************************************
           CORRECTION FACTORS AS FUNCTION OF IMAGE NUMBER & RESOLUTION
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED      720
 DEGREES OF FREEDOM OF CHI^2 FIT             81319.0
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      0.228
 NUMBER OF CYCLES CARRIED OUT                      3

 CORRECTION FACTORS for visual inspection by XDS-Viewer DECAY.cbf           
 XMIN=     0.6 XMAX=   179.7 NXBIN=   36
 YMIN= 0.00041 YMAX= 0.33044 NYBIN=   20
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     162425


 ******************************************************************************
  CORRECTION FACTORS AS FUNCTION OF X (fast) & Y(slow) IN THE DETECTOR PLANE
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED     3249
 DEGREES OF FREEDOM OF CHI^2 FIT             81230.3
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      0.228
 NUMBER OF CYCLES CARRIED OUT                      3

 CORRECTION FACTORS for visual inspection by XDS-Viewer MODPIX.cbf          
 XMIN=     4.7 XMAX=  3066.8 NXBIN=   57
 YMIN=     4.6 YMAX=  3068.4 NYBIN=   57
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     162425


 ******************************************************************************
   CORRECTION FACTORS AS FUNCTION OF IMAGE NUMBER & DETECTOR SURFACE POSITION
 ******************************************************************************

 RECIPROCAL CORRECTION FACTORS FOR INPUT DATA SETS MERGED TO
 OUTPUT FILE: XDS_ASCII.HKL                                     

 THE CALCULATIONS ASSUME         FRIEDEL'S_LAW= TRUE
 TOTAL NUMBER OF CORRECTION FACTORS DEFINED      468
 DEGREES OF FREEDOM OF CHI^2 FIT             81347.0
 CHI^2-VALUE OF FIT OF CORRECTION FACTORS      0.227
 NUMBER OF CYCLES CARRIED OUT                      3

 CORRECTION FACTORS for visual inspection by XDS-Viewer ABSORP.cbf          
 XMIN=     0.6 XMAX=   179.7 NXBIN=   36
 DETECTOR_SURFACE_POSITION=    1536    1537
 DETECTOR_SURFACE_POSITION=    2056    2057
 DETECTOR_SURFACE_POSITION=    1016    2057
 DETECTOR_SURFACE_POSITION=    1016    1016
 DETECTOR_SURFACE_POSITION=    2056    1016
 DETECTOR_SURFACE_POSITION=    2713    2024
 DETECTOR_SURFACE_POSITION=    2023    2714
 DETECTOR_SURFACE_POSITION=    1048    2714
 DETECTOR_SURFACE_POSITION=     359    2024
 DETECTOR_SURFACE_POSITION=     359    1049
 DETECTOR_SURFACE_POSITION=    1048     359
 DETECTOR_SURFACE_POSITION=    2023     359
 DETECTOR_SURFACE_POSITION=    2713    1049
 NUMBER OF REFLECTIONS USED FOR DETERMINING CORRECTION FACTORS     162425


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
 1.505E+01  4.123E-02    1.27


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

   49.467  16.420     0.18   1.19     98.84     64.81       6    1171       0
   16.420  11.944     0.51   1.17     97.20     62.58      32    1928       0
   11.944   9.849     0.48   0.91     82.04     58.74      64    2562       0
    9.849   8.572     0.35   1.05     64.81     54.03      88    3115       0
    8.572   7.690     0.31   0.88     67.53     55.57     142    3544       0
    7.690   7.034     0.36   0.94     75.99     58.18     166    3990       0
    7.034   6.522     0.36   0.94     70.92     58.02     212    4310       0
    6.522   6.107     0.31   1.08     83.70     60.71     258    4503       0
    6.107   5.763     0.23   1.14     87.06     62.14     278    4745       0
    5.763   5.471     0.20   1.21    101.07     67.99     306    5081       0
    5.471   5.219     0.24   1.31     93.29     65.48     310    5161       0
    5.219   4.999     0.28   1.36    111.65     74.88     318    5393       0
    4.999   4.805     0.30   1.31    106.57     72.58     338    5560       0
    4.805   4.632     0.29   1.26    104.84     72.43     376    5962       0
    4.632   4.476     0.26   1.14     96.90     70.39     438    6198       0
    4.476   4.335     0.29   1.13     79.57     64.08     472    6419       0
    4.335   4.206     0.29   1.03     89.13     68.75     498    6625       0
    4.206   4.089     0.25   0.93     83.78     67.02     530    6755       0
    4.089   3.980     0.21   0.85     85.34     73.68     564    7032       0
    3.980   3.880     0.20   0.95     94.81     74.40     630    7307       0
    3.880   3.787     0.20   0.82     91.59     74.84     628    7411       0
    3.787   3.701     0.21   0.77     98.90     87.62     626    7445       0
    3.701   3.620     0.19   0.85    110.82     93.32     766    7937       0
    3.620   3.544     0.16   0.83    123.97    105.90     738    7872       0
    3.544   3.473     0.15   0.70    107.96    105.43     728    7999       0
    3.473   3.406     0.13   0.62    126.99    127.92     818    8403       0
    3.406   3.342     0.11   0.49    145.00    176.24     802    8319       0
    3.342   3.282     0.09   0.47    133.78    160.98     878    8531       0
    3.282   3.225     0.07   0.47    136.36    168.95     910    8716       0
    3.225   3.171     0.06   0.45    162.55    208.85     906    8749       0
    3.171   3.120     0.05   0.44    222.04    287.91     986    9111       0
    3.120   3.071     0.06   0.35    192.52    297.89     926    9000       0
    3.071   3.024     0.03   0.34    223.05    334.90    1080    9399       0
    3.024   2.980     0.02   0.32    270.46    436.20    1032    9419       0
    2.980   2.937     0.02   0.28    602.97   1035.09    1084    9517       0
    2.937   2.896     0.01   0.28    385.47    663.89    1072    9594       0
    2.896   2.857     0.01   0.25    384.05    726.64    1148    9752       0
    2.857   2.819     0.00   0.24    477.51    928.71    1182    9945       0
    2.819   2.783     0.00   0.23    399.55    760.60    1180   10080       0
    2.783   2.748    -0.00   0.20   1654.08   3626.45    1250   10158       0
    2.748   2.714    -0.00   0.18   3147.55   7307.89    1188   10133       0
    2.714   2.682    -0.00   0.17    478.49   1173.90    1250   10396       0
    2.682   2.651    -0.00   0.17   1602.58   3866.32    1286   10487       0
    2.651   2.620    -0.01   0.16  18357.01  44813.16    1318   10541       0
    2.620   2.591    -0.01   0.15    -99.90    -99.90    1326   10764       0
    2.591   2.563    -0.00   0.16    -99.90    -99.90    1362   10714       0
    2.563   2.536    -0.01   0.16    -99.90    -99.90    1420   10950       0
    2.536   2.509    -0.01   0.15    -99.90    -99.90    1342   10893       0
    2.509   2.484    -0.00   0.14   2191.35   5800.56    1484   11190       0
    2.484   2.459    -0.00   0.15   3159.69   7899.67    1430   11245       0
    2.459   2.434     0.00   0.14   1640.81   4300.45    1516   11228       0
    2.434   2.411    -0.00   0.16    -99.90    -99.90    1442   11388       0
    2.411   2.388     0.00   0.14   5844.32  15250.31    1552   11351       0
    2.388   2.366     0.00   0.15   2484.33   6251.29    1482   11653       0
    2.366   2.344    -0.01   0.15   3331.79   8461.68    1634   11582       0
    2.344   2.324     0.00   0.14    -99.90    -99.90    1544   11725       0
    2.324   2.303     0.00   0.15   1000.97   2587.30    1572   11849       0
    2.303   2.283     0.01   0.13   2444.50   6669.03    1648   11920       0
    2.283   2.264     0.01   0.15    -99.90    -99.90    1614   12008       0
    2.264   2.245     0.01   0.14   1521.54   4095.54    1688   12059       0
    2.245   2.226     0.00   0.15   2956.17   7464.08    1668   12190       0
    2.226   2.208     0.01   0.13   1104.07   2900.23    1730   12189       0
    2.208   2.191     0.00   0.14  13902.88  37476.34    1770   12364       0
    2.191   2.174     0.01   0.14   5732.74  15337.98    1658   12292       0
    2.174   2.157     0.01   0.14   1195.93   3088.00    1844   12499       0
    2.157   2.141     0.01   0.14    -99.90    -99.90    1762   12495       0
    2.141   2.125     0.01   0.13    428.92   1025.92    1818   12623       0
    2.125   2.109     0.02   0.14   1439.96   3829.10    1800   12545       0
    2.109   2.094     0.01   0.13    999.07   2662.32    1696   12162       0
    2.094   2.079     0.02   0.14    686.26   1865.16    1678   11833       0
    2.079   2.064     0.02   0.14    559.79   1466.70    1548   11292       0
    2.064   2.050     0.02   0.13    710.59   1953.24    1572   11455       0
    2.050   2.036     0.02   0.13    601.85   1660.18    1424   10974       0
    2.036   2.022     0.02   0.14    765.59   2073.84    1446   10891       0
    2.022   2.008     0.01   0.14    379.13    936.37    1394   10697       0
    2.008   1.995     0.01   0.13   3050.19   8225.28    1376   10069       0
    1.995   1.982     0.01   0.14   1239.32   3266.57    1216    9407       0
    1.982   1.969     0.01   0.13   2784.93   7768.92    1378    8980       0
    1.969   1.957     0.01   0.13   2345.43   6386.97    1192    8322       0
    1.957   1.945     0.01   0.13    829.85   2240.84    1166    8107       0
    1.945   1.933     0.01   0.14    -99.90    -99.90    1190    7935       0
    1.933   1.921    -0.00   0.13   1601.19   4292.21    1078    7349       0
    1.921   1.909    -0.00   0.12    -99.90    -99.90    1068    7254       0
    1.909   1.898     0.00   0.15   2001.46   5021.20    1040    6903       0
    1.898   1.887    -0.01   0.13    -99.90    -99.90     934    6495       0
    1.887   1.876    -0.00   0.14    -99.90    -99.90     952    6514       0
    1.876   1.865     0.00   0.13    -99.90    -99.90     946    6067       0
    1.865   1.854    -0.01   0.13    -99.90    -99.90     822    5816       0
    1.854   1.844    -0.00   0.13    -99.90    -99.90     852    5766       0
    1.844   1.834    -0.01   0.13   2039.14   5319.56     782    5427       0
    1.834   1.823    -0.01   0.12    -99.90    -99.90     750    5016       0
    1.823   1.814     0.00   0.13   1427.51   3963.21     736    5101       0
    1.814   1.804    -0.01   0.13   1552.34   4183.16     662    4413       0
    1.804   1.794    -0.01   0.12   1744.92   4692.24     312    3731       0
    1.794   1.785    -0.01   0.07    357.41   1214.13      34    2891       0
    1.785   1.775    -0.03   0.00    -99.90    -99.90       0    2397       0
    1.775   1.766     0.01   0.00    -99.90    -99.90       0    2272       0
    1.766   1.757    -0.01   0.00    -99.90    -99.90       0    2121       0
    1.757   1.748    -0.00   0.00    -99.90    -99.90       0    1720       0
    1.748   1.740    -0.01   0.00    -99.90    -99.90       0     693       0
   --------------------------------------------------------------------------
   49.467   1.740     0.05   0.26    144.93    212.46   97158  818061       0
 


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
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1      21
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

     5.19        4203    4203     44000        9.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68        7394    7394     79410        9.3%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.01        9655    9655    102616        9.4%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.61       11177   11177    121474        9.2%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.33       12258   12258    137994        8.9%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.13       13102   13102    152134        8.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.97       11618   11618    165654        7.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.84        7243    7243    177756        4.1%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.74        3309    3309    189244        1.7%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total       79959   79959   1170282        6.8%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES     79959
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                        79959
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                  79959



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1      41
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

     5.19        8635    8635     44000       19.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68       15531   15531     79410       19.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.01       20222   20222    102616       19.7%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.61       23349   23349    121474       19.2%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.33       25659   25659    137994       18.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.13       27322   27322    152134       18.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.97       24257   24257    165654       14.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.84       14942   14942    177756        8.4%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.74        6782    6782    189244        3.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total      166699  166699   1170282       14.2%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    166699
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       166699
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 166699



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1      61
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

     5.19       13395   13395     44000       30.4%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68       24127   24127     79410       30.4%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.01       31376   31376    102616       30.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.61       36571   36571    121474       30.1%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.33       40607   40607    137994       29.4%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.13       43616   43616    152134       28.7%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.97       39166   39166    165654       23.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.84       24578   24578    177756       13.8%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.74       11380   11380    189244        6.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total      264816  264816   1170282       22.6%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    264816
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       264816
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 264816



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1      81
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

     5.19       18073   18073     44000       41.1%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68       32577   32577     79410       41.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.01       42334   42334    102616       41.3%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.61       49440   49440    121474       40.7%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     2.33       54992   54988    137994       39.8%     185.5%    244.3%        8   -0.00    262.3%     1.5      0    0.000       0
     2.13       59229   59221    152134       38.9%     -99.9%    -99.9%       16    0.00    -99.9%    58.4      0    0.000       0
     1.97       53325   53322    165654       32.2%     -99.9%    -99.9%        6    0.00    -99.9%    22.1      0    0.000       0
     1.84       33414   33414    177756       18.8%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.74       15450   15450    189244        8.2%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total      358834  358819   1170282       30.7%     -99.9%    -99.9%       30    0.00    -99.9%    18.1      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    358834
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       358834
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 358819



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     100
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

     5.19       22452   22452     44000       51.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68       40522   40522     79410       51.0%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.01       52557   52553    102616       51.2%      79.7%     94.0%        8    0.12    112.7%    13.7      0    0.000       0
     2.61       61376   61362    121474       50.5%     200.1%    295.3%       28    0.01    283.0%     5.5      0    0.000       0
     2.33       68263   68240    137994       49.5%     416.8%    766.7%       46   -0.00    589.5%    -7.3      0    0.000       0
     2.13       73518   73477    152134       48.3%     -99.9%    -99.9%       82    0.00    -99.9%     4.4      0    0.000       0
     1.97       66164   66145    165654       39.9%     622.6%   2623.0%       38    0.02    880.5%    50.1      0    0.000       0
     1.84       41440   41440    177756       23.3%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     1.74       19173   19173    189244       10.1%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total      445465  445364   1170282       38.1%     410.9%    896.8%      202    0.07    581.2%    14.5      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    445465
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       445465
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 445364



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     120
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

     5.19       27132   27132     44000       61.7%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
     3.68       48871   48860     79410       61.5%      62.8%     55.0%       22    0.28     88.9%     0.5      0    0.000       0
     3.01       63347   63298    102616       61.7%      80.3%     81.1%       98    0.11    113.6%    21.1      0    0.000       0
     2.61       74110   74019    121474       60.9%     240.2%    295.2%      182    0.00    339.7%     0.5      0    0.000       0
     2.33       82637   82494    137994       59.8%     756.8%   1549.5%      286   -0.00   1070.3%   -13.0      0    0.000       0
     2.13       89294   89103    152134       58.6%    3768.9%   9148.2%      382    0.01   5330.0%    -2.5      0    0.000       0
     1.97       80560   80414    165654       48.5%     420.8%   1060.6%      292    0.02    595.1%     8.9      0    0.000       0
     1.84       50798   50787    177756       28.6%     -99.9%    -99.9%       22    0.00    -99.9%     9.0      0    0.000       0
     1.74       23673   23673    189244       12.5%     -99.9%    -99.9%        0    0.00    -99.9%     0.0      0    0.000       0
    total      540422  539780   1170282       46.1%     173.6%    294.4%     1284    0.06    245.5%    30.2*     0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    540422
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       540422
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 539780



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     140
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

     5.19       31643   31642     44000       71.9%      65.0%     53.6%        2    0.35     92.0%     0.0      0    0.000       0
     3.68       57032   56954     79410       71.7%      88.6%     62.9%      156    0.27    125.3%   -22.3      0    0.000       0
     3.01       73974   73744    102616       71.9%      91.9%     85.5%      460    0.10    130.0%     0.8      0    0.000       0
     2.61       86672   86286    121474       71.0%     300.2%    481.6%      772    0.00    424.5%    -3.1      0    0.000       0
     2.33       96782   96258    137994       69.8%    1360.9%   3117.5%     1048   -0.00   1924.6%    -3.6      0    0.000       0
     2.13      104707  104023    152134       68.4%     739.2%   1954.1%     1368    0.01   1045.4%     4.9      0    0.000       0
     1.97       94627   93936    165654       56.7%    6272.7%  15641.6%     1382    0.02   8870.9%    -0.3      0    0.000       0
     1.84       59879   59528    177756       33.5%    2743.9%   6923.4%      702    0.00   3880.4%     3.2      0    0.000       0
     1.74       27964   27864    189244       14.7%     -99.9%    -99.9%      200    0.00    -99.9%    13.7      0    0.000       0
    total      633280  630235   1170282       53.9%     207.9%    369.4%     6090    0.06    294.0%     7.6      0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    633280
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       633280
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 630235



 ******************************************************************************
  SUMMARY OF DATA SET STATISTICS FOR IMAGE   DATA_RANGE=       1     160
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

     5.19       36248   36205     44000       82.3%     105.8%     67.2%       86    0.32    149.6%   -33.7      0    0.000       0
     3.68       65288   64918     79410       81.8%      82.9%     64.0%      740    0.25    117.2%    -2.9      0    0.000       0
     3.01       84735   83809    102616       81.7%     105.8%    104.3%     1852    0.09    149.6%     9.4      0    0.000       0
     2.61       99323   97749    121474       80.5%     345.8%    634.2%     3148    0.00    489.1%     6.5      0    0.000       0
     2.33      111012  108689    137994       78.8%    3406.3%   8247.8%     4646   -0.00   4817.2%    -2.3      0    0.000       0
     2.13      120217  116987    152134       76.9%    1335.6%   3363.0%     6460    0.01   1888.8%    -0.1      0    0.000       0
     1.97      108798  105152    165654       63.5%    1512.9%   3895.4%     7292    0.02   2139.5%     2.4      0    0.000       0
     1.84       69012   66363    177756       37.3%     -99.9%    -99.9%     5298    0.00    -99.9%    -0.2      0    0.000       0
     1.74       32287   31549    189244       16.7%   13656.0%  37537.8%     1476   -0.01  19312.5%     4.9      0    0.000       0
    total      726920  711421   1170282       60.8%     234.6%    446.3%    30998    0.05    331.8%    12.4*     0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    726920
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       726920
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 711421



 ******************************************************************************
  STATISTICS OF SAVED DATA SET "XDS_ASCII.HKL" (DATA_RANGE=       1     180)
 FILE TYPE:         XDS_ASCII      MERGE=FALSE          FRIEDEL'S_LAW=FALSE
 ******************************************************************************

   REFLECTIONS OF TYPE H,0,0  0,K,0  0,0,L OR EXPECTED TO BE ABSENT (*)
   --------------------------------------------------------------------

   H    K    L  RESOLUTION  INTENSITY     SIGMA    INTENSITY/SIGMA  #OBSERVED

    0    0    4    34.487  -0.3503E+02  0.5978E+02        -0.59           2 
    0    0    5    27.590   0.1228E+02  0.6011E+02         0.20           1 
    0    0    6    22.991  -0.7288E+01  0.6824E+02        -0.11           1 
    0    0    7    19.707  -0.1403E+02  0.6630E+02        -0.21           1 
    0    0    8    17.244  -0.8558E+01  0.6972E+02        -0.12           1 
    0    0    9    15.328  -0.3461E+01  0.8245E+02        -0.04           1 
    0    0   10    13.795  -0.3625E+02  0.9008E+02        -0.40           2 
    0    0   11    12.541   0.4351E+02  0.9933E+02         0.44           2 
    0    0   12    11.496   0.6056E+03  0.4598E+03         1.32           2 
    0    0   13    10.611   0.4553E+04  0.2787E+04         1.63           2 
    0    0   14     9.853   0.1429E+04  0.8979E+03         1.59           2 
    0    0   15     9.197   0.1196E+03  0.2918E+03         0.41           2 
    0    0   16     8.622   0.2400E+03  0.2715E+03         0.88           2 
    0    0   17     8.115   0.3127E+02  0.9745E+02         0.32           2 
    0    0   18     7.664   0.8915E+03  0.6798E+03         1.31           2 
    0    0   19     7.260  -0.1664E+02  0.9752E+02        -0.17           2 
    0    0   20     6.897   0.2264E+03  0.2092E+03         1.08           2 
    0    0   21     6.569   0.1685E+04  0.9879E+03         1.71           2 
    0    0   22     6.270   0.5074E+03  0.3923E+03         1.29           2 
    0    0   23     5.998   0.9129E+03  0.6844E+03         1.33           2 
    0    0   24     5.748   0.6794E+03  0.5178E+03         1.31           2 
    0    0   25     5.518  -0.5766E+02  0.1724E+03        -0.33           2 
    0    0   26     5.306  -0.7435E+02  0.1696E+03        -0.44           2 
    0    0   27     5.109  -0.1167E+03  0.1744E+03        -0.67           1 
    0    0   28     4.927   0.3358E+02  0.1531E+03         0.22           2 
    0    0   29     4.757   0.9259E+02  0.4033E+03         0.23           2 
    0    0   30     4.598  -0.1312E+03  0.1832E+03        -0.72           2 
    0    0   31     4.450   0.4137E+02  0.1555E+03         0.27           2 
    0    0   32     4.311  -0.3013E+01  0.3423E+03        -0.01           1 
    0    0   33     4.180   0.5477E+01  0.1319E+03         0.04           2 
    0    0   34     4.057   0.1592E+03  0.1629E+03         0.98           2 
    0    0   35     3.941  -0.8531E+02  0.1396E+03        -0.61           2 
    0    0   36     3.832   0.9878E+02  0.1502E+03         0.66           2 
    0    0   37     3.728  -0.7277E+02  0.1154E+03        -0.63           2 
    0    0   38     3.630   0.5676E+02  0.1543E+03         0.37           2 
    0    0   39     3.537  -0.7680E+02  0.1682E+03        -0.46           2 
    0    0   40     3.449   0.7385E+02  0.1768E+03         0.42           2 
    0    0   41     3.365  -0.4456E+02  0.1994E+03        -0.22           2 
    0    0   43     3.208   0.1148E+02  0.1885E+03         0.06           2 
    0    0   44     3.135  -0.6982E+01  0.1438E+03        -0.05           2 
    0    0   45     3.066   0.3485E+01  0.1604E+03         0.02           2 
    0    0   46     2.999  -0.6724E+02  0.1719E+03        -0.39           2 
    0    0   47     2.935   0.5150E+02  0.1903E+03         0.27           2 
    0    0   48     2.874  -0.7311E+02  0.2017E+03        -0.36           2 
    0    0   49     2.815  -0.1476E+02  0.2064E+03        -0.07           2 
    0    0   50     2.759  -0.1271E+02  0.2126E+03        -0.06           2 
    0    0   51     2.705   0.8722E+02  0.2064E+03         0.42           2 
    0    0   52     2.653   0.6711E+02  0.1876E+03         0.36           2 
    0    0   53     2.603   0.6258E+02  0.1896E+03         0.33           2 
    0    0   54     2.555   0.4735E+02  0.2016E+03         0.23           2 
    0    0   55     2.508  -0.6755E+02  0.2044E+03        -0.33           2 
    0    0   56     2.463   0.4228E+02  0.1986E+03         0.21           2 
    0    0   57     2.420  -0.1131E+02  0.2077E+03        -0.05           2 
    0    0   58     2.378   0.4845E+02  0.2128E+03         0.23           2 
    0    0   59     2.338   0.1203E+03  0.2357E+03         0.51           2 
    0    0   60     2.299   0.5474E+02  0.2182E+03         0.25           2 
    0    0   61     2.261  -0.3113E+02  0.2341E+03        -0.13           2 
    0    0   62     2.225  -0.1513E+02  0.2358E+03        -0.06           2 
    0    0   63     2.190   0.1203E+03  0.2489E+03         0.48           2 
    0    0   64     2.155   0.9207E+02  0.2483E+03         0.37           2 
    0    0   65     2.122   0.5792E+02  0.2436E+03         0.24           2 
    0    0   66     2.090  -0.9918E+02  0.2476E+03        -0.40           2 
    0    0   67     2.059   0.5762E+02  0.2435E+03         0.24           2 
    0    0   68     2.029  -0.1081E+03  0.2782E+03        -0.39           2 
    0    0   69     1.999   0.3658E+02  0.2981E+03         0.12           2 
    0    0   70     1.971  -0.1909E+03  0.3547E+03        -0.54           2 
    0    0   71     1.943   0.2532E+03  0.4245E+03         0.60           1 
    0    0   72     1.916   0.1180E+02  0.3912E+03         0.03           1 
    0    0   73     1.890  -0.2427E+03  0.4227E+03        -0.57           1 
    0    0   74     1.864   0.1365E+03  0.3531E+03         0.39           1 
    0    0   75     1.839   0.9859E+02  0.3461E+03         0.28           1 
    0    0   76     1.815  -0.2274E+02  0.3492E+03        -0.07           1 
    0    2    0    49.467   0.9299E+00  0.5024E+01         0.19           1 
    0    3    0    32.978   0.2617E+01  0.8044E+01         0.33           2 
    0    4    0    24.734   0.1715E+02  0.2060E+02         0.83           2 
    0    5    0    19.787   0.2226E+02  0.7157E+02         0.31           2 
    0    6    0    16.489  -0.1688E+02  0.1243E+03        -0.14           2 
    0    7    0    14.133   0.2528E+02  0.3546E+02         0.71           2 
    0    8    0    12.367   0.1956E+02  0.3235E+02         0.60           2 
    0    9    0    10.993   0.1162E+03  0.9196E+02         1.26           2 
    0   10    0     9.893   0.1610E+03  0.3014E+03         0.53           2 
    0   11    0     8.994  -0.9577E+02  0.1129E+03        -0.85           2 
    0   12    0     8.245  -0.5248E+01  0.2459E+03        -0.02           2 
    0   13    0     7.610   0.9753E+01  0.1672E+03         0.06           2 
    0   14    0     7.067  -0.3633E+02  0.2046E+03        -0.18           2 
    0   15    0     6.596  -0.3836E+02  0.1475E+03        -0.26           2 
    0   16    0     6.183  -0.7783E+02  0.9406E+02        -0.83           2 
    0   17    0     5.820  -0.5208E+02  0.6256E+02        -0.83           2 
    0   18    0     5.496   0.4074E+02  0.7422E+02         0.55           2 
    0   19    0     5.207  -0.1205E+03  0.1476E+03        -0.82           2 
    0   20    0     4.947  -0.3980E+02  0.1017E+03        -0.39           2 
    0   21    0     4.711  -0.1978E+02  0.9373E+02        -0.21           1 
    0   22    0     4.497  -0.8668E-01  0.1015E+03        -0.00           2 
    0   23    0     4.301   0.3057E+03  0.2609E+03         1.17           2 
    0   24    0     4.122  -0.4133E+02  0.1393E+03        -0.30           2 
    0   25    0     3.957  -0.2376E+02  0.1705E+03        -0.14           2 
    0   26    0     3.805  -0.1162E+03  0.2199E+03        -0.53           1 
    0   27    0     3.664   0.9167E+02  0.1914E+03         0.48           2 
    0   28    0     3.533  -0.2528E+02  0.1766E+03        -0.14           2 
    0   29    0     3.412  -0.1496E+03  0.1975E+03        -0.76           2 
    0   30    0     3.298   0.2133E+03  0.2192E+03         0.97           2 
    0   31    0     3.191   0.1084E+03  0.1658E+03         0.65           2 
    0   32    0     3.092   0.1012E+03  0.1529E+03         0.66           2 
    0   33    0     2.998  -0.1056E+03  0.1485E+03        -0.71           2 
    0   34    0     2.910  -0.4619E+02  0.1554E+03        -0.30           2 
    0   35    0     2.827   0.1415E+01  0.1768E+03         0.01           2 
    0   36    0     2.748  -0.3185E+02  0.1309E+03        -0.24           2 
    0   37    0     2.674   0.4644E+02  0.1370E+03         0.34           2 
    0   38    0     2.604   0.5527E+02  0.1550E+03         0.36           2 
    0   39    0     2.537   0.3288E+02  0.2014E+03         0.16           2 
    0   40    0     2.473  -0.3432E+02  0.1729E+03        -0.20           2 
    0   41    0     2.413   0.7697E+02  0.1745E+03         0.44           2 
    0   42    0     2.356  -0.3082E+02  0.1747E+03        -0.18           2 
    0   43    0     2.301   0.7265E+02  0.1732E+03         0.42           2 
    0   44    0     2.249   0.6245E+02  0.1922E+03         0.32           2 
    0   45    0     2.199   0.4283E+02  0.1764E+03         0.24           2 
    0   46    0     2.151  -0.4090E+02  0.1842E+03        -0.22           2 
    0   47    0     2.105  -0.1416E+02  0.1825E+03        -0.08           2 
    0   48    0     2.061   0.9660E+02  0.2116E+03         0.46           2 
    0   49    0     2.019  -0.7017E+02  0.2006E+03        -0.35           2 
    0   50    0     1.979   0.9450E+02  0.2464E+03         0.38           1 
    0   51    0     1.940   0.8200E+01  0.1752E+03         0.05           2 
    0   52    0     1.903  -0.9602E+02  0.1744E+03        -0.55           2 
    0   53    0     1.867   0.1054E+03  0.1809E+03         0.58           2 
    0   54    0     1.832   0.5615E+02  0.1679E+03         0.33           2 
    0   55    0     1.799  -0.7588E+01  0.2122E+03        -0.04           1 
    0   56    0     1.767  -0.7733E+02  0.2146E+03        -0.36           1 
    2    0    0    47.793  -0.5550E+01  0.8457E+01        -0.66           2 
    3    0    0    31.862   0.6777E+01  0.1457E+02         0.47           2 
    4    0    0    23.897   0.6722E+02  0.5613E+02         1.20           2 
    5    0    0    19.117  -0.5872E+02  0.1178E+03        -0.50           2 
    6    0    0    15.931   0.6507E+02  0.7818E+02         0.83           2 
    7    0    0    13.655  -0.1959E+00  0.4967E+02        -0.00           2 
    8    0    0    11.948  -0.3350E+01  0.3428E+02        -0.10           2 
    9    0    0    10.621   0.1639E+02  0.3783E+02         0.43           2 
   10    0    0     9.559   0.5382E+02  0.7588E+02         0.71           2 
   11    0    0     8.690   0.1847E+03  0.1258E+03         1.47           2 
   12    0    0     7.966   0.2636E+02  0.8607E+02         0.31           2 
   13    0    0     7.353   0.7693E+02  0.1090E+03         0.71           2 
   14    0    0     6.828  -0.5077E+02  0.1328E+03        -0.38           2 
   15    0    0     6.372   0.3327E+01  0.6970E+02         0.05           2 
   16    0    0     5.974   0.2088E+03  0.1460E+03         1.43           2 
   17    0    0     5.623  -0.4686E+02  0.1140E+03        -0.41           2 
   18    0    0     5.310  -0.4094E+01  0.1061E+03        -0.04           2 
   19    0    0     5.031  -0.4386E+02  0.2111E+03        -0.21           2 
   20    0    0     4.779   0.1034E+03  0.2566E+03         0.40           2 
   21    0    0     4.552   0.3804E+03  0.2660E+03         1.43           2 
   22    0    0     4.345   0.1506E+03  0.1892E+03         0.80           2 
   23    0    0     4.156  -0.8699E+02  0.3130E+03        -0.28           2 
   24    0    0     3.983   0.6466E+02  0.3630E+03         0.18           2 
   25    0    0     3.823   0.1920E+03  0.3280E+03         0.59           2 
   26    0    0     3.676  -0.6579E+02  0.4299E+03        -0.15           2 
   27    0    0     3.540   0.6070E+02  0.2863E+03         0.21           2 
   28    0    0     3.414   0.1301E+03  0.3711E+03         0.35           2 
   29    0    0     3.296   0.8713E+02  0.2197E+03         0.40           2 
   30    0    0     3.186   0.6464E+02  0.2176E+03         0.30           2 
   31    0    0     3.083  -0.1636E+02  0.1841E+03        -0.09           2 
   32    0    0     2.987   0.2230E+03  0.2099E+03         1.06           2 
   33    0    0     2.897   0.3110E+02  0.1861E+03         0.17           2 
   34    0    0     2.811  -0.1752E+02  0.2029E+03        -0.09           2 
   35    0    0     2.731   0.8765E+01  0.2456E+03         0.04           2 
   36    0    0     2.655   0.1875E+03  0.2037E+03         0.92           2 
   37    0    0     2.583   0.5862E+02  0.1768E+03         0.33           2 
   38    0    0     2.515   0.5097E+02  0.1694E+03         0.30           2 
   39    0    0     2.451   0.1817E+02  0.1867E+03         0.10           2 
   40    0    0     2.390   0.5061E+01  0.1677E+03         0.03           2 
   41    0    0     2.331  -0.9839E+02  0.1673E+03        -0.59           2 
   42    0    0     2.276   0.1805E+03  0.1838E+03         0.98           2 
   43    0    0     2.223   0.1000E+03  0.1800E+03         0.56           2 
   44    0    0     2.172  -0.4230E+02  0.1829E+03        -0.23           2 
   45    0    0     2.124   0.1937E+02  0.1587E+03         0.12           2 
   46    0    0     2.078  -0.1563E+02  0.2404E+03        -0.07           1 
   47    0    0     2.034   0.6308E+02  0.2720E+03         0.23           1 

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

     5.19       40683   39734     44000       90.3%      81.1%     60.0%     1898    0.29    114.7%    -0.3      0    0.000       0
     3.68       73340   70564     79410       88.9%      92.8%     71.8%     5552    0.24    131.2%    -2.1      0    0.000       0
     3.01       95249   90401    102616       88.1%     136.9%    153.0%     9696    0.09    193.6%     2.2      0    0.000       0
     2.61      111693  105057    121474       86.5%     604.6%   1257.1%    13272    0.00    855.1%     1.4      0    0.000       0
     2.33      124927  116797    137994       84.6%   67986.4% 172942.4%    16260   -0.00  96147.8%    -1.4      0    0.000       0
     2.13      135286  125895    152134       82.8%    1671.9%   4330.3%    18782    0.01   2364.4%    -0.4      0    0.000       0
     1.97      122557  114118    165654       68.9%     862.0%   2310.7%    16878    0.02   1219.0%    10.1*     0    0.000       0
     1.84       77864   72151    177756       40.6%    7432.3%  19832.0%    11426    0.00  10510.9%    -0.3      0    0.000       0
     1.74       36462   34765    189244       18.4%    3312.4%   9005.4%     3394   -0.01   4684.4%     2.1      0    0.000       0
    total      818061  769482   1170282       65.8%     144.9%    212.5%    97158    0.05    205.0%    11.8*     0    0.000       0


 NUMBER OF REFLECTIONS IN SELECTED SUBSET OF IMAGES    818061
 NUMBER OF REJECTED MISFITS                                 0
 NUMBER OF SYSTEMATIC ABSENT REFLECTIONS                    0
 NUMBER OF ACCEPTED OBSERVATIONS                       818061
 NUMBER OF UNIQUE ACCEPTED REFLECTIONS                 769482



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

   WILSON LINE (using all data) : A=   7.005 B=  50.540 CORRELATION=  0.88
      #      RES      SS        <I>       log(<I>)       BO
    7138     9.645   0.003  1.8296E+03       7.512     -94.4
   12784     6.092   0.007  7.9917E+02       6.684      23.8
   16447     4.760   0.011  8.3024E+02       6.722      12.8
   19402     4.035   0.015  5.3832E+02       6.288      23.3
   21915     3.564   0.020  2.5353E+02       5.535      37.3
   24186     3.226   0.024  6.9817E+01       4.246      57.4
   26274     2.969   0.028  1.7982E+01       2.889      72.6
   28165     2.765   0.033  3.1488E+00       1.147      89.6
   29927     2.598   0.037 -4.7589E-01    -999.999   -1000.0
   31543     2.458   0.041 -4.6414E-02    -999.999   -1000.0
   33104     2.339   0.046  1.3942E+00       0.332      73.0
   34598     2.235   0.050  5.4751E+00       1.700      53.0
   35858     2.144   0.054  4.8156E+00       1.572      49.9
   36758     2.063   0.059  7.1116E+00       1.962      42.9
   32065     1.993   0.063  4.9479E+00       1.599      42.9
   24402     1.927   0.067  3.9836E+00       1.382      41.7
   19862     1.867   0.072 -8.5409E-01    -999.999   -1000.0
   15687     1.814   0.076 -1.6698E+00    -999.999   -1000.0
    9826     1.767   0.080 -1.2239E+00    -999.999   -1000.0


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF  CENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         3<I>**2     15<I>**3    105<I>**4 

       0   overall        0.000        0.000        0.000


 HIGHER ORDER MOMENTS OF WILSON DISTRIBUTION OF ACENTRIC DATA
    AS COMPARED WITH THEORETICAL VALUES. (EXPECTED: 1.00)    
      #      RES        <I**2>/      <I**3>/      <I**4>/  
                         2<I>**2      6<I>**3     24<I>**4 

    7138     9.645        3.951       25.729      239.828
   12784     6.092        3.967       37.905      652.788
   16447     4.760        2.673        9.488       44.515
   19402     4.035        3.277       15.401      113.158
   21915     3.564        4.677       62.116     2208.052
   24186     3.226        8.736       52.962      576.617
   26274     2.969       38.187     1190.144   137934.572
   28165     2.765      589.066    31843.024  8224824.007
   33104     2.339     2057.626    34932.388 12413380.777
   34598     2.235     1153.068  2310753.100*************
   35858     2.144      303.882    83460.085 53005583.464
   36758     2.063      147.073     7635.624  2376375.308
   32065     1.993      430.050    95233.624 43452545.959
   24402     1.927      731.259   175288.051 92891772.817
  459941   overall      378.398   201417.483341404707.785

   ======= CUMULATIVE INTENSITY DISTRIBUTION =======
 DEFINITIONS:
   <I>    = mean reflection intensity
 Na(Z)exp = expected number of acentric reflections with I <= Z*<I>
 Na(Z)obs = observed number of acentric reflections with I <= Z*<I>
 Nc(Z)exp = expected number of  centric reflections with I <= Z*<I>
 Nc(Z)obs = observed number of  centric reflections with I <= Z*<I>



 Nc(Z)obs/Nc(Z)exp versus resolution and Z (0.1-1.0)
      #      RES     0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   0.9   1.0

       0   overall  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00  0.00


 Na(Z)obs/Na(Z)exp versus resolution and Z (0.1-1.0)
      #      RES     0.1   0.2   0.3   0.4   0.5   0.6   0.7   0.8   0.9   1.0

    7138     9.645  5.49  3.28  2.46  2.03  1.76  1.59  1.45  1.36  1.29  1.23
   12784     6.092  4.81  2.93  2.23  1.87  1.65  1.49  1.38  1.30  1.24  1.19
   16447     4.760  4.27  2.59  2.00  1.69  1.51  1.38  1.29  1.22  1.17  1.12
   19402     4.035  4.39  2.59  1.98  1.67  1.49  1.36  1.28  1.21  1.15  1.11
   21915     3.564  4.53  2.60  1.95  1.63  1.44  1.32  1.24  1.17  1.12  1.08
   24186     3.226  4.92  2.68  1.94  1.58  1.37  1.23  1.14  1.07  1.02  0.98
   26274     2.969  5.30  2.82  1.99  1.59  1.35  1.19  1.08  1.00  0.94  0.89
   28165     2.765  5.33  2.81  1.97  1.55  1.30  1.14  1.03  0.94  0.88  0.82
   33104     2.339  5.25  2.76  1.93  1.52  1.28  1.11  1.00  0.92  0.85  0.80
   34598     2.235  5.19  2.74  1.92  1.52  1.28  1.12  1.01  0.93  0.87  0.82
   35858     2.144  5.08  2.68  1.88  1.49  1.25  1.09  0.99  0.91  0.84  0.80
   36758     2.063  5.03  2.66  1.87  1.48  1.25  1.09  0.99  0.91  0.85  0.80
   32065     1.993  5.11  2.69  1.89  1.49  1.26  1.10  0.99  0.91  0.85  0.80
   24402     1.927  5.26  2.77  1.94  1.53  1.29  1.13  1.01  0.93  0.87  0.82
  459941   overall  3.86  2.09  1.50  1.20  1.03  0.91  0.83  0.77  0.72  0.69


 List of    500 reflections *NOT* obeying Wilson distribution (Z> 10.0)

   h    k    l     RES      Z     Intensity    Sigma

    5  -30   41    2.48 9672.85 -0.4490E+03  0.4865E+03 "alien"
   11   11  -52    2.46 9337.17 -0.4334E+03  0.4190E+03 "alien"
    3   10  -55    2.45 9239.04 -0.4288E+03  0.4483E+03 "alien"
   25  -36   28    2.51 8335.45 -0.3869E+03  0.5080E+03 "alien"
   19  -44    5    2.51 8059.22 -0.3741E+03  0.3864E+03 "alien"
   20  -23   45    2.45 8032.95 -0.3728E+03  0.3707E+03 "alien"
   43  -15   -2    2.48 7854.91 -0.3646E+03  0.3992E+03 "alien"
    4  -29   43    2.45 7839.19 -0.3638E+03  0.4310E+03 "alien"
   24  -21   49    2.26 7739.23  0.4237E+05  0.3346E+05 "alien"
   14  -37   32    2.48 7687.73 -0.3568E+03  0.4255E+03 "alien"
   24   11   30    2.49 7671.55 -0.3561E+03  0.3775E+03 "alien"
   21  -38   30    2.44 7668.61 -0.3559E+03  0.4176E+03 "alien"
   17    5   44    2.52 7526.43 -0.3493E+03  0.2945E+03 "alien"
   43  -22   10    2.41 7523.50 -0.3492E+03  0.3543E+03 "alien"
    4    6  -56    2.45 7437.28 -0.3452E+03  0.3981E+03 "alien"
   10   -5   54    2.44 7251.52 -0.3366E+03  0.4274E+03 "alien"
   43  -24   -7    2.49 7166.06 -0.3326E+03  0.2500E+03 "alien"
    8  -27   47    2.41 7086.74 -0.3289E+03  0.6319E+03 "alien"
   30  -35   28    2.43 7013.43 -0.3255E+03  0.5460E+03 "alien"
    2   -6  -56    2.43 7010.26 -0.3254E+03  0.2980E+03 "alien"
    6    9  -55    2.45 6868.48 -0.3188E+03  0.3369E+03 "alien"
   13    7  -53    2.45 6856.39 -0.3182E+03  0.2676E+03 "alien"
    6   -2  -57    2.43 6802.01 -0.3157E+03  0.5502E+03 "alien"
   17  -35   34    2.50 6779.19 -0.3147E+03  0.3926E+03 "alien"
   13   -6   53    2.42 6749.19 -0.3133E+03  0.4490E+03 "alien"
    7   24  -43    2.47 6747.23 -0.3132E+03  0.3343E+03 "alien"
   19  -27   44    2.42 6701.05 -0.3110E+03  0.3613E+03 "alien"
   39  -28   19    2.44 6695.89 -0.3108E+03  0.4352E+03 "alien"
   15  -28   42    2.52 6688.33 -0.3104E+03  0.3192E+03 "alien"
    3   17   49    2.42 6651.10 -0.3087E+03  0.3274E+03 "alien"
    6   -3  -55    2.51 6618.58 -0.3072E+03  0.5068E+03 "alien"
    3  -18   53    2.42 6613.57 -0.3070E+03  0.6014E+03 "alien"
   26  -17   42    2.46 6507.40 -0.3020E+03  0.3186E+03 "alien"
    4   27  -41    2.47 6497.53 -0.3016E+03  0.3635E+03 "alien"
   15  -36   34    2.47 6455.28 -0.2996E+03  0.6590E+03 "alien"
   39  -23  -29    2.45 6446.97 -0.2992E+03  0.2419E+03 "alien"
    3   -1   55    2.49 6445.52 -0.2992E+03  0.3046E+03 "alien"
   12   -9   54    2.40 6441.91 -0.2990E+03  0.5382E+03 "alien"
   11   18   41    2.45 6381.66 -0.2962E+03  0.3369E+03 "alien"
    7    5   52    2.49 6358.68 -0.2951E+03  0.3322E+03 "alien"
    3    1   55    2.47 6303.03 -0.2925E+03  0.3899E+03 "alien"
    4   -4   56    2.44 6302.46 -0.2925E+03  0.3108E+03 "alien"
    7   -8   56    2.40 6291.61 -0.2920E+03  0.3257E+03 "alien"
   27   -6   40    2.45 6284.93 -0.2917E+03  0.2858E+03 "alien"
    3   -2  -57    2.43 6235.22 -0.2894E+03  0.3771E+03 "alien"
   20  -18   47    2.45 6189.16 -0.2873E+03  0.3661E+03 "alien"
   16  -42   24    2.41 6182.77 -0.2870E+03  0.4123E+03 "alien"
    3  -39   20    2.49 6174.57 -0.2866E+03  0.2654E+03 "alien"
    8   18  -47    2.52 6173.98 -0.2866E+03  0.3624E+03 "alien"
   20   -1  -52    2.44 6165.94 -0.2862E+03  0.3127E+03 "alien"
    0   12   52    2.49 6162.91 -0.2860E+03  0.3847E+03 "alien"
    3   17  -52    2.44 6150.77 -0.2855E+03  0.3309E+03 "alien"
   30   -7   36    2.45 6131.34 -0.2846E+03  0.2768E+03 "alien"
   13   24   30    2.45 6128.54 -0.2845E+03  0.2297E+03 "alien"
   11  -26   45    2.50 6122.60 -0.2842E+03  0.4186E+03 "alien"
   22  -18   45    2.47 6119.30 -0.2840E+03  0.3721E+03 "alien"
   22  -41   24    2.43 6090.45 -0.2827E+03  0.3277E+03 "alien"
   15    4   47    2.49 6073.35 -0.2819E+03  0.3370E+03 "alien"
   14  -35   35    2.48 6069.49 -0.2817E+03  0.4865E+03 "alien"
   11   -1   51    2.52 6067.62 -0.2816E+03  0.3510E+03 "alien"
   32  -39   15    2.48 6059.85 -0.2813E+03  0.3037E+03 "alien"
   30  -12   39    2.40 6059.45 -0.2812E+03  0.2778E+03 "alien"
    6    1  -56    2.47 6012.53 -0.2791E+03  0.3531E+03 "alien"
   14    4   49    2.45 6000.29 -0.2785E+03  0.3100E+03 "alien"
    1   40  -14    2.40 5991.74 -0.2781E+03  0.2656E+03 "alien"
    2    5  -56    2.46 5984.69 -0.2778E+03  0.5659E+03 "alien"
   42   -7  -14    2.41 5966.51 -0.2769E+03  0.2907E+03 "alien"
   37  -35    8    2.53 5960.48 -0.2767E+03  0.2694E+03 "alien"
    9   -3  -55    2.50 5954.08 -0.2764E+03  0.3532E+03 "alien"
    8   21  -45    2.49 5933.70 -0.2754E+03  0.3091E+03 "alien"
    5   18  -50    2.46 5929.71 -0.2752E+03  0.2714E+03 "alien"
    9  -13  -55    2.41 5912.35 -0.2744E+03  0.3508E+03 "alien"
    8  -25   46    2.50 5910.26 -0.2743E+03  0.7176E+03 "alien"
   32   10   13    2.43 5878.29 -0.2728E+03  0.2467E+03 "alien"
   17  -11   50    2.45 5859.67 -0.2720E+03  0.3727E+03 "alien"
    7  -11  -54    2.48 5849.13 -0.2715E+03  0.3412E+03 "alien"
    5  -26   47    2.43 5844.82 -0.2713E+03  0.3078E+03 "alien"
   10  -12  -55    2.43 5837.60 -0.2709E+03  0.3510E+03 "alien"
   15  -25   46    2.46 5826.87 -0.2704E+03  0.3019E+03 "alien"
   41  -29   10    2.46 5814.01 -0.2699E+03  0.2252E+03 "alien"
    9   -8  -55    2.48 5809.71 -0.2697E+03  0.4320E+03 "alien"
   15    8  -51    2.47 5782.34 -0.2684E+03  0.3179E+03 "alien"
   42   -7    3    2.41 5781.18 -0.2683E+03  0.2392E+03 "alien"
    1  -23   47    2.50 5779.41 -0.2682E+03  0.3461E+03 "alien"
    6   -9  -55    2.46 5774.59 -0.2680E+03  0.3669E+03 "alien"
    7  -25   46    2.50 5765.40 -0.2676E+03  0.6061E+03 "alien"
   11  -25   47    2.46 5761.66 -0.2674E+03  0.3657E+03 "alien"
   19  -34   35    2.49 5756.23 -0.2672E+03  0.2683E+03 "alien"
   37    2    9    2.45 5741.34 -0.2665E+03  0.2294E+03 "alien"
   20    8   39    2.50 5728.47 -0.2659E+03  0.2761E+03 "alien"
   21  -15   48    2.41 5728.02 -0.2659E+03  0.3687E+03 "alien"
   19   21   23    2.49 5716.02 -0.2653E+03  0.3029E+03 "alien"
   17  -41   24    2.46 5681.93 -0.2637E+03  0.4107E+03 "alien"
   24  -42   17    2.48 5649.93 -0.2622E+03  0.3074E+03 "alien"
   12    4   49    2.51 5642.61 -0.2619E+03  0.2579E+03 "alien"
   30   13    6    2.51 5635.61 -0.2616E+03  0.2929E+03 "alien"
   21  -40   23    2.50 5633.46 -0.2615E+03  0.2982E+03 "alien"
   26  -32   35    2.43 5632.07 -0.2614E+03  0.3202E+03 "alien"
   14  -23   48    2.44 5627.09 -0.2612E+03  0.2842E+03 "alien"
   37  -25   26    2.42 5623.49 -0.2610E+03  0.2869E+03 "alien"
   27  -45    7    2.40 5619.31 -0.2608E+03  0.2807E+03 "alien"
    8   -1  -57    2.42 5598.63 -0.2599E+03  0.3417E+03 "alien"
   22   19   20    2.49 5598.01 -0.2598E+03  0.2396E+03 "alien"
    4  -19   51    2.47 5597.23 -0.2598E+03  0.6044E+03 "alien"
   37  -33   17    2.46 5591.57 -0.2595E+03  0.3816E+03 "alien"
   11    4   50    2.49 5586.83 -0.2593E+03  0.2584E+03 "alien"
    7   -3  -56    2.47 5585.89 -0.2593E+03  0.5211E+03 "alien"
   27  -29   38    2.40 5569.12 -0.2585E+03  0.3272E+03 "alien"
    8  -19   50    2.50 5565.30 -0.2583E+03  0.3277E+03 "alien"
   30   14   -5    2.52 5545.05 -0.2574E+03  0.2065E+03 "alien"
    4   -3   55    2.48 5521.24 -0.2563E+03  0.2935E+03 "alien"
   17  -42   18    2.51 5512.03 -0.2558E+03  0.4117E+03 "alien"
   29  -36   26    2.47 5509.79 -0.2557E+03  0.3522E+03 "alien"
    2   16   50    2.43 5504.11 -0.2555E+03  0.2404E+03 "alien"
   18  -44   11    2.48 5503.74 -0.2555E+03  0.3372E+03 "alien"
    5   26  -42    2.46 5500.43 -0.2553E+03  0.3102E+03 "alien"
   38  -17   21    2.51 5499.89 -0.2553E+03  0.2612E+03 "alien"
   37   -3   16    2.48 5497.91 -0.2552E+03  0.2393E+03 "alien"
   31  -38   19    2.48 5493.37 -0.2550E+03  0.3454E+03 "alien"
   28  -21   39    2.46 5483.68 -0.2545E+03  0.2917E+03 "alien"
   12   34  -10    2.40 5477.36 -0.2542E+03  0.3510E+03 "alien"
   19   12   39    2.42 5464.82 -0.2536E+03  0.2514E+03 "alien"
   18  -37   31    2.49 5462.29 -0.2535E+03  0.6224E+03 "alien"
   10   31  -26    2.47 5455.89 -0.2532E+03  0.2451E+03 "alien"
    2   -1   57    2.41 5453.39 -0.2531E+03  0.2847E+03 "alien"
   15  -25   47    2.42 5449.38 -0.2529E+03  0.3116E+03 "alien"
   26   -4   41    2.43 5445.31 -0.2527E+03  0.2719E+03 "alien"
   36   -1  -27    2.49 5419.35 -0.2515E+03  0.2136E+03 "alien"
    5   16  -52    2.44 5409.60 -0.2511E+03  0.2694E+03 "alien"
   21  -44   15    2.43 5409.26 -0.2511E+03  0.3427E+03 "alien"
   11   -6  -54    2.52 5408.61 -0.2510E+03  0.3179E+03 "alien"
   37  -29   20    2.49 5388.11 -0.2501E+03  0.2626E+03 "alien"
    7  -10   55    2.43 5387.14 -0.2500E+03  0.3024E+03 "alien"
    7   -3  -57    2.42 5376.80 -0.2496E+03  0.5232E+03 "alien"
    6   -8   54    2.49 5373.59 -0.2494E+03  0.3045E+03 "alien"
   37  -29   23    2.43 5359.25 -0.2487E+03  0.2583E+03 "alien"
   30  -44   -5    2.42 5349.34 -0.2483E+03  0.2322E+03 "alien"
   33  -42    9    2.41 5348.82 -0.2483E+03  0.3305E+03 "alien"
   20  -19   47    2.44 5342.92 -0.2480E+03  0.3267E+03 "alien"
   10    9  -53    2.47 5341.03 -0.2479E+03  0.3360E+03 "alien"
    4  -23   50    2.41 5340.21 -0.2479E+03  0.3783E+03 "alien"
   12  -33   40    2.42 5331.53 -0.2475E+03  0.4032E+03 "alien"
   36  -35   12    2.52 5324.85 -0.2471E+03  0.2653E+03 "alien"
    8    0  -56    2.46 5316.56 -0.2468E+03  0.3550E+03 "alien"
   22    0   43    2.46 5315.04 -0.2467E+03  0.2571E+03 "alien"
    2   31  -36    2.48 5310.28 -0.2465E+03  0.3249E+03 "alien"
   21    9   38    2.46 5305.64 -0.2463E+03  0.2667E+03 "alien"
   32  -40   14    2.46 5301.78 -0.2461E+03  0.3243E+03 "alien"
   39  -18   18    2.52 5291.40 -0.2456E+03  0.2704E+03 "alien"
   12    1   51    2.47 5285.65 -0.2453E+03  0.2557E+03 "alien"
   17  -43   19    2.44 5279.78 -0.2451E+03  0.4023E+03 "alien"
    2   31  -35    2.51 5275.68 -0.2449E+03  0.3199E+03 "alien"
    7   25  -41    2.49 5275.18 -0.2448E+03  0.3163E+03 "alien"
   19    0   46    2.47 5268.29 -0.2445E+03  0.3443E+03 "alien"
   15   30  -18    2.44 5266.00 -0.2444E+03  0.2172E+03 "alien"
    7  -27   47    2.41 5261.65 -0.2442E+03  0.3672E+03 "alien"
    6   -1  -57    2.43 5252.05 -0.2438E+03  0.5185E+03 "alien"
   33   -9   32    2.45 5250.25 -0.2437E+03  0.2503E+03 "alien"
    7  -35   35    2.46 5246.55 -0.2435E+03  0.2797E+03 "alien"
   13   -7   52    2.46 5240.79 -0.2432E+03  0.4035E+03 "alien"
   30  -18   36    2.50 5237.28 -0.2431E+03  0.2577E+03 "alien"
   38  -18   24    2.45 5228.92 -0.2427E+03  0.2380E+03 "alien"
   34  -39    5    2.52 5215.65 -0.2421E+03  0.2549E+03 "alien"
    7   -8   55    2.44 5208.06 -0.2417E+03  0.2998E+03 "alien"
    9  -14  -53    2.48 5203.06 -0.2415E+03  0.3493E+03 "alien"
   19  -44   15    2.44 5197.40 -0.2412E+03  0.3750E+03 "alien"
   14  -33   38    2.48 5195.19 -0.2411E+03  0.4754E+03 "alien"
   18  -20   48    2.43 5168.68 -0.2399E+03  0.3310E+03 "alien"
   13  -14   51    2.48 5145.03 -0.2388E+03  0.3231E+03 "alien"
   21   17   28    2.45 5140.92 -0.2386E+03  0.2767E+03 "alien"
   23  -32   35    2.50 5119.02 -0.2376E+03  0.3396E+03 "alien"
    3   13  -55    2.41 5108.15 -0.2371E+03  0.2831E+03 "alien"
   37   -3   20    2.41 5099.25 -0.2367E+03  0.3317E+03 "alien"
   32   -3   29    2.49 5098.19 -0.2366E+03  0.2663E+03 "alien"
   15  -41   25    2.44 5094.63 -0.2365E+03  0.3943E+03 "alien"
   31   13  -12    2.49 5094.39 -0.2365E+03  0.2886E+03 "alien"
   20  -25   44    2.45 5092.46 -0.2364E+03  0.3089E+03 "alien"
   39   -4    9    2.48 5091.55 -0.2363E+03  0.2417E+03 "alien"
   17  -24   47    2.42 5089.64 -0.2362E+03  0.3635E+03 "alien"
    8   -3  -57    2.42 5084.63 -0.2360E+03  0.2932E+03 "alien"
    6   -5   55    2.46 5080.47 -0.2358E+03  0.3354E+03 "alien"
   29   15  -14    2.51 5079.12 -0.2357E+03  0.2141E+03 "alien"
   42   -8    1    2.44 5077.69 -0.2357E+03  0.2269E+03 "alien"
    4   -3  -56    2.47 5074.78 -0.2355E+03  0.3388E+03 "alien"
    8   -2  -55    2.51 5068.13 -0.2352E+03  0.3276E+03 "alien"
   24  -39   23    2.51 5066.32 -0.2351E+03  0.2949E+03 "alien"
    9  -43    4    2.49 5065.70 -0.2351E+03  0.3051E+03 "alien"
   44  -18    4    2.41 5060.50 -0.2349E+03  0.3315E+03 "alien"
   11  -31   42    2.44 5054.25 -0.2346E+03  0.6682E+03 "alien"
    3  -22   48    2.51 5051.94 -0.2345E+03  0.2921E+03 "alien"
   38  -11   22    2.45 5051.12 -0.2344E+03  0.2864E+03 "alien"
    2  -41   -1    2.46 5050.28 -0.2344E+03  0.2009E+03 "alien"
   33  -36   23    2.42 5047.36 -0.2343E+03  0.3121E+03 "alien"
   21  -42   22    2.43 5042.86 -0.2341E+03  0.3600E+03 "alien"
   20   -4   47    2.45 5041.42 -0.2340E+03  0.2724E+03 "alien"
   33   -4   27    2.50 5038.84 -0.2339E+03  0.2406E+03 "alien"
    6  -23   50    2.41 5035.25 -0.2337E+03  0.6150E+03 "alien"
   15    2   50    2.41 5033.48 -0.2336E+03  0.2623E+03 "alien"
   27   -2  -47    2.41 5028.25 -0.2334E+03  0.2326E+03 "alien"
    4   18   46    2.48 5026.07 -0.2333E+03  0.3101E+03 "alien"
   18  -39   27    2.49 5020.05 -0.2330E+03  0.4377E+03 "alien"
   31  -22   34    2.49 5012.92 -0.2327E+03  0.2596E+03 "alien"
   33   -6   31    2.43 5009.89 -0.2325E+03  0.2580E+03 "alien"
   19    8  -48    2.47 5004.07 -0.2323E+03  0.2209E+03 "alien"
    2   17  -52    2.45 5002.07 -0.2322E+03  0.2785E+03 "alien"
    3   30  -38    2.45 5001.38 -0.2321E+03  0.3069E+03 "alien"
   18   20  -36    2.48 4980.34 -0.2312E+03  0.2186E+03 "alien"
   19  -14   47    2.51 4975.15 -0.2309E+03  0.3632E+03 "alien"
    9    8   49    2.51 4974.02 -0.2309E+03  0.2478E+03 "alien"
   24  -21   44    2.42 4970.77 -0.2307E+03  0.3733E+03 "alien"
    9   -6   53    2.50 4964.80 -0.2304E+03  0.3748E+03 "alien"
   18  -41   23    2.47 4954.84 -0.2300E+03  0.3328E+03 "alien"
    5   37   -5    2.50 4954.77 -0.2300E+03  0.2024E+03 "alien"
   18  -41   22    2.49 4944.67 -0.2295E+03  0.3473E+03 "alien"
    3   29  -40    2.44 4940.45 -0.2293E+03  0.3252E+03 "alien"
   42  -31  -13    2.42 4932.58 -0.2289E+03  0.2225E+03 "alien"
   39  -11   21    2.41 4930.12 -0.2288E+03  0.2688E+03 "alien"
   18  -31   39    2.49 4926.65 -0.2287E+03  0.2585E+03 "alien"
   32  -10  -43    2.42 4925.94 -0.2286E+03  0.2056E+03 "alien"
   15   -7  -55    2.44 4924.35 -0.2286E+03  0.2586E+03 "alien"
   18    4  -51    2.46 4909.73 -0.2279E+03  0.2912E+03 "alien"
    2  -35   34    2.42 4907.60 -0.2278E+03  0.2246E+03 "alien"
   21  -19   46    2.45 4905.31 -0.2277E+03  0.3333E+03 "alien"
   34  -35   22    2.43 4903.22 -0.2276E+03  0.2735E+03 "alien"
   25  -39   28    2.40 4901.95 -0.2275E+03  0.2775E+03 "alien"
   19   11   39    2.45 4901.77 -0.2275E+03  0.3284E+03 "alien"
    5   11  -53    2.51 4897.54 -0.2273E+03  0.2615E+03 "alien"
   19  -15   48    2.46 4896.55 -0.2273E+03  0.3484E+03 "alien"
   21   11  -42    2.52 4896.50 -0.2273E+03  0.2375E+03 "alien"
    9   24   36    2.44 4895.18 -0.2272E+03  0.3266E+03 "alien"
   22    4   41    2.45 4884.91 -0.2267E+03  0.3077E+03 "alien"
   21  -18  -50    2.45 4884.72 -0.2267E+03  0.2407E+03 "alien"
   40  -22   15    2.51 4880.86 -0.2265E+03  0.2428E+03 "alien"
   10  -26   46    2.47 4880.37 -0.2265E+03  0.4800E+03 "alien"
   42  -17    4    2.52 4877.01 -0.2264E+03  0.2258E+03 "alien"
   30  -17   37    2.47 4869.18 -0.2260E+03  0.2474E+03 "alien"
   17  -41   21    2.51 4864.76 -0.2258E+03  0.4323E+03 "alien"
    8  -10   54    2.46 4862.75 -0.2257E+03  0.2945E+03 "alien"
   21  -41   25    2.42 4853.70 -0.2253E+03  0.3500E+03 "alien"
   41  -15  -23    2.47 4851.69 -0.2252E+03  0.2062E+03 "alien"
   17  -40   25    2.49 4846.68 -0.2250E+03  0.3961E+03 "alien"
   29  -41   19    2.42 4844.89 -0.2249E+03  0.2884E+03 "alien"
   32  -31   26    2.52 4842.80 -0.2248E+03  0.2488E+03 "alien"
   12  -41   25    2.43 4840.95 -0.2247E+03  0.2810E+03 "alien"
   15  -43   18    2.45 4840.21 -0.2247E+03  0.2654E+03 "alien"
   19   20   28    2.42 4836.16 -0.2245E+03  0.1977E+03 "alien"
   27  -35   31    2.43 4831.40 -0.2242E+03  0.2989E+03 "alien"
   23   -8  -50    2.47 4826.17 -0.2240E+03  0.2859E+03 "alien"
    6  -18   53    2.41 4823.90 -0.2239E+03  0.4395E+03 "alien"
    9  -30   43    2.44 4818.00 -0.2236E+03  0.3691E+03 "alien"
    9   -5   55    2.41 4816.91 -0.2236E+03  0.4629E+03 "alien"
    7   11  -53    2.48 4812.32 -0.2234E+03  0.3824E+03 "alien"
   32    0   27    2.47 4810.76 -0.2233E+03  0.2578E+03 "alien"
    1  -32   35    2.52 4810.09 -0.2233E+03  0.2231E+03 "alien"
   19   13  -46    2.40 4808.96 -0.2232E+03  0.2315E+03 "alien"
   19  -44    4    2.52 4808.40 -0.2232E+03  0.3378E+03 "alien"
    7   30   29    2.41 4808.04 -0.2232E+03  0.2964E+03 "alien"
    4  -24   47    2.49 4803.80 -0.2230E+03  0.2864E+03 "alien"
   19  -39   31    2.40 4798.84 -0.2227E+03  0.5198E+03 "alien"
    2   19  -49    2.51 4794.88 -0.2226E+03  0.2482E+03 "alien"
    6  -20   52    2.41 4788.40 -0.2222E+03  0.4685E+03 "alien"
    3   -1  -55    2.52 4787.97 -0.2222E+03  0.3318E+03 "alien"
   19  -12   50    2.40 4780.44 -0.2219E+03  0.3498E+03 "alien"
    2   16  -53    2.43 4776.01 -0.2217E+03  0.2773E+03 "alien"
   20  -37   29    2.52 4773.62 -0.2216E+03  0.2724E+03 "alien"
   21   19  -34    2.44 4769.73 -0.2214E+03  0.2152E+03 "alien"
    1   -1   55    2.50 4767.43 -0.2213E+03  0.2583E+03 "alien"
   24  -36   30    2.48 4767.26 -0.2213E+03  0.2832E+03 "alien"
   25  -21  -45    2.50 4763.53 -0.2211E+03  0.2513E+03 "alien"
    8   -5   54    2.47 4762.76 -0.2211E+03  0.2946E+03 "alien"
   18  -44    2    2.52 4760.91 -0.2210E+03  0.2812E+03 "alien"
    7  -44    2    2.40 4759.15 -0.2209E+03  0.2019E+03 "alien"
   21   -7   45    2.52 4750.91 -0.2205E+03  0.2839E+03 "alien"
   23    5   40    2.41 4743.55 -0.2202E+03  0.2514E+03 "alien"
   35  -30   24    2.47 4742.72 -0.2201E+03  0.2470E+03 "alien"
    5   30   29    2.48 4737.04 -0.2199E+03  0.1971E+03 "alien"
   13  -23   49    2.42 4736.23 -0.2198E+03  0.2985E+03 "alien"
   18   -1   47    2.48 4733.40 -0.2197E+03  0.2581E+03 "alien"
    0   15  -53    2.47 4730.92 -0.2196E+03  0.3320E+03 "alien"
   12  -39   28    2.47 4730.23 -0.2195E+03  0.2894E+03 "alien"
   41  -22   13    2.48 4728.82 -0.2195E+03  0.2526E+03 "alien"
    3   24  -46    2.45 4728.60 -0.2195E+03  0.2440E+03 "alien"
    6    2  -57    2.43 4728.01 -0.2194E+03  0.3304E+03 "alien"
   13  -43   11    2.51 4725.50 -0.2193E+03  0.2447E+03 "alien"
   37   -4   19    2.45 4704.45 -0.2184E+03  0.2336E+03 "alien"
    1   30   35    2.44 4702.70 -0.2183E+03  0.2639E+03 "alien"
   13   17   39    2.48 4700.30 -0.2182E+03  0.2269E+03 "alien"
    3  -12   55    2.44 4700.07 -0.2181E+03  0.5284E+03 "alien"
   12   -4   51    2.52 4697.11 -0.2180E+03  0.4924E+03 "alien"
    1   36  -25    2.49 4692.99 -0.2178E+03  0.2042E+03 "alien"
   24    6  -43    2.50 4688.96 -0.2176E+03  0.2145E+03 "alien"
   42  -18   11    2.46 4685.82 -0.2175E+03  0.2530E+03 "alien"
   24  -44   15    2.41 4683.48 -0.2174E+03  0.3507E+03 "alien"
   34  -30   24    2.51 4683.37 -0.2174E+03  0.2479E+03 "alien"
   23   17   22    2.49 4682.86 -0.2174E+03  0.2025E+03 "alien"
   41  -26   16    2.42 4679.08 -0.2172E+03  0.3114E+03 "alien"
   25  -14  -49    2.44 4677.58 -0.2171E+03  0.2639E+03 "alien"
   26   14  -30    2.50 4673.62 -0.2169E+03  0.2117E+03 "alien"
   17   19  -39    2.48 4673.52 -0.2169E+03  0.3328E+03 "alien"
   39   -2    7    2.45 4673.52 -0.2169E+03  0.2280E+03 "alien"
   20  -33   36    2.49 4673.28 -0.2169E+03  0.2609E+03 "alien"
   18   19   31    2.43 4671.82 -0.2168E+03  0.2078E+03 "alien"
   28  -39   19    2.51 4670.78 -0.2168E+03  0.2696E+03 "alien"
   34  -36   16    2.51 4663.47 -0.2165E+03  0.2474E+03 "alien"
   12   19   37    2.51 4662.51 -0.2164E+03  0.2216E+03 "alien"
   29  -27   36    2.44 4660.58 -0.2163E+03  0.2783E+03 "alien"
   12  -30   44    2.41 4652.26 -0.2159E+03  0.4882E+03 "alien"
    2    1   57    2.40 4650.07 -0.2158E+03  0.2604E+03 "alien"
    7  -44    3    2.40 4641.40 -0.2154E+03  0.2473E+03 "alien"
   43  -28    4    2.43 4640.13 -0.2154E+03  0.2688E+03 "alien"
   19  -20  -50    2.44 4617.86 -0.2143E+03  0.2525E+03 "alien"
   40  -34   -6    2.48 4616.39 -0.2143E+03  0.2117E+03 "alien"
   22    7   38    2.47 4610.30 -0.2140E+03  0.2603E+03 "alien"
   13   -9   53    2.42 4608.73 -0.2139E+03  0.3123E+03 "alien"
   40  -27   17    2.44 4608.46 -0.2139E+03  0.3029E+03 "alien"
   33  -21   33    2.45 4604.22 -0.2137E+03  0.2741E+03 "alien"
   11   -4   54    2.41 4601.59 -0.2136E+03  0.3210E+03 "alien"
    7   33  -24    2.50 4600.80 -0.2135E+03  0.3228E+03 "alien"
   17  -37   32    2.47 4593.80 -0.2132E+03  0.6764E+03 "alien"
   25  -11   42    2.50 4592.18 -0.2131E+03  0.2611E+03 "alien"
    0   27   38    2.51 4579.57 -0.2126E+03  0.2488E+03 "alien"
   33  -41    3    2.48 4575.81 -0.2124E+03  0.2838E+03 "alien"
   38    1    9    2.41 4573.61 -0.2123E+03  0.2280E+03 "alien"
   10  -40   25    2.46 4563.71 -0.2118E+03  0.2730E+03 "alien"
    7    2   55    2.40 4562.21 -0.2118E+03  0.3115E+03 "alien"
   13   14  -50    2.41 4562.12 -0.2117E+03  0.2811E+03 "alien"
   11   -2   53    2.44 4561.42 -0.2117E+03  0.2923E+03 "alien"
   23  -30   40    2.41 4561.26 -0.2117E+03  0.3066E+03 "alien"
    9   -4   54    2.45 4557.05 -0.2115E+03  0.3812E+03 "alien"
    4   27  -42    2.44 4556.95 -0.2115E+03  0.3027E+03 "alien"
    8   19  -46    2.52 4556.93 -0.2115E+03  0.3361E+03 "alien"
   19   21  -36    2.40 4548.14 -0.2111E+03  0.2016E+03 "alien"
    7  -19   52    2.43 4545.87 -0.2110E+03  0.4131E+03 "alien"
   36  -24   27    2.45 4536.04 -0.2105E+03  0.2620E+03 "alien"
   29  -11   38    2.47 4535.12 -0.2105E+03  0.2587E+03 "alien"
   43  -18    3    2.47 4528.23 -0.2102E+03  0.2981E+03 "alien"
    8  -37   33    2.42 4527.87 -0.2102E+03  0.2638E+03 "alien"
   14  -36   34    2.47 4521.37 -0.2099E+03  0.3476E+03 "alien"
   34   -1   23    2.47 4517.23 -0.2097E+03  0.2441E+03 "alien"
   33  -37   21    2.42 4514.14 -0.2095E+03  0.3164E+03 "alien"
    5   33   21    2.50 4510.98 -0.2094E+03  0.2479E+03 "alien"
    9   -1   52    2.52 4495.87 -0.2087E+03  0.3612E+03 "alien"
    1   10   53    2.47 4488.47 -0.2083E+03  0.3375E+03 "alien"
    2    2  -56    2.47 4483.19 -0.2081E+03  0.4064E+03 "alien"
   36  -10   26    2.46 4480.30 -0.2079E+03  0.2755E+03 "alien"
   30  -22   38    2.41 4479.94 -0.2079E+03  0.2785E+03 "alien"
   16   -1   50    2.42 4473.99 -0.2077E+03  0.2714E+03 "alien"
    4    5  -56    2.46 4470.52 -0.2075E+03  0.3287E+03 "alien"
   10   -2  -57    2.41 4469.70 -0.2075E+03  0.2849E+03 "alien"
   11   19   40    2.45 4465.41 -0.2073E+03  0.2124E+03 "alien"
   30   -8   35    2.49 4462.50 -0.2071E+03  0.2458E+03 "alien"
   19    9   40    2.48 4462.07 -0.2071E+03  0.2617E+03 "alien"
    2   23  -46    2.50 4461.32 -0.2071E+03  0.2407E+03 "alien"
   41  -15  -25    2.44 4458.29 -0.2069E+03  0.1973E+03 "alien"
   22  -24   42    2.50 4457.90 -0.2069E+03  0.3294E+03 "alien"
   12  -30   43    2.44 4457.01 -0.2069E+03  0.4656E+03 "alien"
   33  -17   33    2.46 4453.61 -0.2067E+03  0.2506E+03 "alien"
    1    7  -57    2.41 4451.91 -0.2066E+03  0.5443E+03 "alien"
    7  -22  -49    2.43 4450.16 -0.2066E+03  0.2728E+03 "alien"
   11   13   47    2.40 4447.76 -0.2064E+03  0.2424E+03 "alien"
   44  -13   -2    2.40 4443.53 -0.2062E+03  0.1943E+03 "alien"
    0   17  -53    2.43 4441.24 -0.2061E+03  0.2961E+03 "alien"
   22  -29   41    2.42 4440.15 -0.2061E+03  0.3063E+03 "alien"
   14  -32   41    2.42 4436.55 -0.2059E+03  0.4920E+03 "alien"
   17  -24   45    2.49 4428.03 -0.2055E+03  0.3281E+03 "alien"
   31  -35   24    2.49 4426.13 -0.2054E+03  0.3177E+03 "alien"
   25   12  -39    2.41 4425.48 -0.2054E+03  0.2130E+03 "alien"
   30    7   21    2.52 4422.76 -0.2053E+03  0.2858E+03 "alien"
   15  -37   33    2.45 4412.49 -0.2048E+03  0.3405E+03 "alien"
   27  -13  -49    2.40 4408.24 -0.2046E+03  0.2069E+03 "alien"
    4  -30   42    2.44 4408.16 -0.2046E+03  0.2776E+03 "alien"
   43  -21    9    2.43 4407.01 -0.2045E+03  0.3084E+03 "alien"
    5  -23   50    2.41 4398.73 -0.2042E+03  0.3737E+03 "alien"
   23    6   38    2.45 4397.92 -0.2041E+03  0.2905E+03 "alien"
   11   -6   52    2.51 4396.33 -0.2041E+03  0.2995E+03 "alien"
   22   10   34    2.50 4396.21 -0.2040E+03  0.2342E+03 "alien"
    2    4  -55    2.51 4396.06 -0.2040E+03  0.4772E+03 "alien"
   35  -33   21    2.47 4395.97 -0.2040E+03  0.2541E+03 "alien"
   37  -23   25    2.46 4395.16 -0.2040E+03  0.2900E+03 "alien"
    6  -25   48    2.43 4392.38 -0.2039E+03  0.3757E+03 "alien"
   11  -14   51    2.51 4383.67 -0.2035E+03  0.3636E+03 "alien"
   12   12   45    2.47 4382.83 -0.2034E+03  0.2206E+03 "alien"
   39  -13  -27    2.50 4381.56 -0.2034E+03  0.2221E+03 "alien"
   11   -3   54    2.41 4381.08 -0.2033E+03  0.4027E+03 "alien"
   31  -25   36    2.40 4378.66 -0.2032E+03  0.2902E+03 "alien"
   26   -1   40    2.41 4374.33 -0.2030E+03  0.2260E+03 "alien"
   23  -28   38    2.52 4374.06 -0.2030E+03  0.2917E+03 "alien"
    1    2   55    2.49 4373.42 -0.2030E+03  0.2689E+03 "alien"
   33   10   -4    2.50 4368.11 -0.2027E+03  0.1770E+03 "alien"
   11    9  -52    2.50 4365.96 -0.2026E+03  0.2130E+03 "alien"
   28  -45    5    2.40 4362.77 -0.2025E+03  0.2787E+03 "alien"
   11  -22   48    2.50 4362.17 -0.2025E+03  0.2541E+03 "alien"
   10  -31   41    2.47 4361.06 -0.2024E+03  0.3555E+03 "alien"
   30   13   13    2.43 4359.57 -0.2023E+03  0.2257E+03 "alien"
   30  -24   34    2.51 4359.46 -0.2023E+03  0.2745E+03 "alien"
    7  -43   14    2.41 4356.03 -0.2022E+03  0.2732E+03 "alien"
   21  -39   30    2.40 4351.84 -0.2020E+03  0.2777E+03 "alien"
   39  -22   23    2.41 4348.46 -0.2018E+03  0.2492E+03 "alien"
   33    4   20    2.46 4347.00 -0.2018E+03  0.2200E+03 "alien"
   33  -42   -3    2.45 4346.90 -0.2018E+03  0.2821E+03 "alien"
   41  -10   11    2.44 4346.31 -0.2017E+03  0.2051E+03 "alien"
   19  -43   15    2.49 4343.47 -0.2016E+03  0.3274E+03 "alien"
   12  -44   -8    2.44 4330.79 -0.2010E+03  0.2234E+03 "alien"
   15   31   -6    2.45 4330.70 -0.2010E+03  0.1883E+03 "alien"
   39  -34   14    2.41 4326.16 -0.2008E+03  0.2524E+03 "alien"
    4   21  -48    2.46 4323.83 -0.2007E+03  0.2442E+03 "alien"
   35  -32   20    2.51 4323.83 -0.2007E+03  0.2484E+03 "alien"
    5   -3   54    2.51 4317.27 -0.2004E+03  0.3175E+03 "alien"
    0    3   55    2.49 4317.11 -0.2004E+03  0.2671E+03 "alien"
   18   18  -40    2.45 4315.16 -0.2003E+03  0.3092E+03 "alien"
   24  -15   43    2.50 4312.58 -0.2002E+03  0.3057E+03 "alien"
   17  -19   47    2.51 4309.53 -0.2000E+03  0.3122E+03 "alien"
   44  -25    4    2.40 4309.22 -0.2000E+03  0.2579E+03 "alien"
    6  -37   33    2.40 4305.19 -0.1998E+03  0.3677E+03 "alien"
   35    9    0    2.41 4304.14 -0.1998E+03  0.1872E+03 "alien"
   32  -42   -5    2.47 4303.91 -0.1998E+03  0.2750E+03 "alien"
    6   37   -3    2.47 4302.10 -0.1997E+03  0.2221E+03 "alien"
    3   20  -50    2.44 4300.95 -0.1996E+03  0.2793E+03 "alien"
   43  -13    7    2.41 4299.68 -0.1996E+03  0.2359E+03 "alien"
   29  -39   24    2.40 4298.57 -0.1995E+03  0.2999E+03 "alien"
   43  -28    7    2.40 4298.40 -0.1995E+03  0.2547E+03 "alien"
   18  -45   -5    2.45 4298.40 -0.1995E+03  0.2577E+03 "alien"
   26  -20   41    2.47 4296.11 -0.1994E+03  0.2495E+03 "alien"
   40  -25   13    2.52 4291.93 -0.1992E+03  0.3117E+03 "alien"
   10   -1  -57    2.41 4291.17 -0.1992E+03  0.2768E+03 "alien"
    3  -19   51    2.47 4287.49 -0.1990E+03  0.3500E+03 "alien"
    3   -4   55    2.49 4282.02 -0.1987E+03  0.2739E+03 "alien"
   15   23  -37    2.44 4281.81 -0.1987E+03  0.3078E+03 "alien"
   19  -45    7    2.45 4281.51 -0.1987E+03  0.3062E+03 "alien"
    8   23   36    2.52 4280.38 -0.1987E+03  0.1951E+03 "alien"
   11  -20   49    2.50 4279.86 -0.1986E+03  0.2612E+03 "alien"
   10   11   48    2.45 4278.97 -0.1986E+03  0.3014E+03 "alien"
   14  -41   22    2.49 4277.57 -0.1985E+03  0.2973E+03 "alien"
   39  -13   21    2.43 4274.26 -0.1984E+03  0.2539E+03 "alien"
   32  -33   29    2.40 4272.16 -0.1983E+03  0.2841E+03 "alien"
   37  -25   25    2.44 4271.81 -0.1983E+03  0.2748E+03 "alien"
   36  -32   19    2.49 4270.83 -0.1982E+03  0.2363E+03 "alien"
   30   11  -26    2.47 4270.64 -0.1982E+03  0.2788E+03 "alien"
   17   -7   50    2.45 4269.15 -0.1981E+03  0.3235E+03 "alien"
   24   -8   45    2.41 4266.77 -0.1980E+03  0.2622E+03 "alien"
   16  -20   48    2.47 4264.81 -0.1979E+03  0.3372E+03 "alien"
   10  -15  -52    2.50 4263.68 -0.1979E+03  0.2911E+03 "alien"
    1  -27   46    2.40 4259.42 -0.1977E+03  0.2560E+03 "alien"
   41  -22  -27    2.41 4254.29 -0.1975E+03  0.2203E+03 "alien"
   29  -17   37    2.51 4248.26 -0.1972E+03  0.2584E+03 "alien"
   14  -37   31    2.50 4248.17 -0.1972E+03  0.3423E+03 "alien"
   11   31  -22    2.50 4244.06 -0.1970E+03  0.2180E+03 "alien"
   37  -40    1    2.41 4243.86 -0.1970E+03  0.2370E+03 "alien"
   28  -41   20    2.42 4243.68 -0.1970E+03  0.2662E+03 "alien"
    5  -21   51    2.43 4242.33 -0.1969E+03  0.5961E+03 "alien"
    3  -20   49    2.53 4237.27 -0.1967E+03  0.3413E+03 "alien"
    8   33   16    2.48 4235.80 -0.1966E+03  0.2282E+03 "alien"
   33  -29   28    2.48 4234.20 -0.1965E+03  0.2636E+03 "alien"
   16   31   -5    2.41 4233.80 -0.1965E+03  0.2928E+03 "alien"
   35  -31   23    2.48 4232.21 -0.1964E+03  0.2583E+03 "alien"
   32    9   13    2.47 4229.92 -0.1963E+03  0.2389E+03 "alien"
    0    9  -54    2.52 4228.23 -0.1962E+03  0.5036E+03 "alien"
   33  -29   30    2.43 4228.01 -0.1962E+03  0.2776E+03 "alien"
   34  -36   20    2.44 4224.73 -0.1961E+03  0.2577E+03 "alien"
   22  -37   28    2.52 4221.22 -0.1959E+03  0.3953E+03 "alien"
   35  -11   28    2.47 4220.78 -0.1959E+03  0.2304E+03 "alien"
   35    2   20    2.40 4214.20 -0.1956E+03  0.2203E+03 "alien"
    5   -2  -56    2.47 4213.71 -0.1956E+03  0.4116E+03 "alien"
    7   13  -54    2.40 4211.75 -0.1955E+03  0.2479E+03 "alien"
   32   12   -9    2.48 4210.95 -0.1954E+03  0.2083E+03 "alien"
   31  -39   17    2.48 4210.50 -0.1954E+03  0.3200E+03 "alien"
    4  -21  -48    2.46 4208.01 -0.1953E+03  0.2661E+03 "alien"
    9  -31   41    2.47 4203.89 -0.1951E+03  0.3644E+03 "alien"
   13  -25   45    2.52 4197.27 -0.1948E+03  0.2565E+03 "alien"
    9  -17   53    2.41 4196.37 -0.1948E+03  0.2501E+03 "alien"
   28  -14   41    2.42 4195.83 -0.1947E+03  0.2621E+03 "alien"
   15  -41   24    2.46 4194.68 -0.1947E+03  0.3882E+03 "alien"
   24    4  -46    2.45 4194.21 -0.1947E+03  0.2337E+03 "alien"
   11    7   50    2.44 4193.65 -0.1946E+03  0.2424E+03 "alien"
    5   -4  -55    2.51 4190.69 -0.1945E+03  0.3100E+03 "alien"
    3    8  -56    2.44 4190.45 -0.1945E+03  0.3323E+03 "alien"
   30  -37   22    2.49 4187.13 -0.1943E+03  0.3430E+03 "alien"
    8    0   54    2.45 4185.45 -0.1943E+03  0.3247E+03 "alien"
    7   32  -32    2.40 4180.92 -0.1941E+03  0.1991E+03 "alien"
   37   -8   25    2.40 4180.25 -0.1940E+03  0.2501E+03 "alien"
   24  -39   24    2.49 4179.10 -0.1940E+03  0.2860E+03 "alien"
   34    8    4    2.48 4177.62 -0.1939E+03  0.2313E+03 "alien"
   13   21  -40    2.51 4177.61 -0.1939E+03  0.2154E+03 "alien"
   37    2    6    2.48 4177.59 -0.1939E+03  0.2041E+03 "alien"
    3  -23   47    2.51 4175.70 -0.1938E+03  0.2691E+03 "alien"
   41  -30    0    2.51 4173.00 -0.1937E+03  0.2802E+03 "alien"
    7   -7  -56    2.44 4170.74 -0.1936E+03  0.3025E+03 "alien"
   37  -29   19    2.51 4165.04 -0.1933E+03  0.3151E+03 "alien"
   16  -39   27    2.50 4162.14 -0.1932E+03  0.3935E+03 "alien"
   15  -17   51    2.41 4160.37 -0.1931E+03  0.3276E+03 "alien"
   27  -29   36    2.46 4159.99 -0.1931E+03  0.3178E+03 "alien"
    6   35  -24    2.42 4159.90 -0.1931E+03  0.2794E+03 "alien"
   41   -7  -15    2.47 4159.71 -0.1931E+03  0.2429E+03 "alien"
   41   -6    7    2.42 4158.32 -0.1930E+03  0.2277E+03 "alien"
   25   14  -36    2.41 4158.06 -0.1930E+03  0.1957E+03 "alien"
   14  -44   -4    2.49 4150.78 -0.1927E+03  0.2630E+03 "alien"
   34  -35   17    2.52 4149.60 -0.1926E+03  0.2542E+03 "alien"
   13  -34   36    2.50 4147.71 -0.1925E+03  0.6081E+03 "alien"
   10  -25   47    2.46 4144.66 -0.1924E+03  0.4464E+03 "alien"
    4    1  -56    2.48 4143.08 -0.1923E+03  0.4971E+03 "alien"


 List of    500 reflections *NOT* obeying Wilson distribution (sorted by resolution)
 Ice rings could occur at (Angstrom):
 3.897,3.669,3.441, 2.671,2.249,2.072, 1.948,1.918,1.883,1.721

   h    k    l     RES      Z     Intensity    Sigma

   24  -21   49    2.26 7739.23  0.4237E+05  0.3346E+05
   19  -39   31    2.40 4798.84 -0.2227E+03  0.5198E+03
   19   21  -36    2.40 4548.14 -0.2111E+03  0.2016E+03
    1  -27   46    2.40 4259.42 -0.1977E+03  0.2560E+03
   27  -29   38    2.40 5569.12 -0.2585E+03  0.3272E+03
   25  -39   28    2.40 4901.95 -0.2275E+03  0.2775E+03
   27  -13  -49    2.40 4408.24 -0.2046E+03  0.2069E+03
    7   -8   56    2.40 6291.61 -0.2920E+03  0.3257E+03
   19  -12   50    2.40 4780.44 -0.2219E+03  0.3498E+03
   28  -45    5    2.40 4362.77 -0.2025E+03  0.2787E+03
   37   -8   25    2.40 4180.25 -0.1940E+03  0.2501E+03
    2    1   57    2.40 4650.07 -0.2158E+03  0.2604E+03
   12   34  -10    2.40 5477.36 -0.2542E+03  0.3510E+03
    7   32  -32    2.40 4180.92 -0.1941E+03  0.1991E+03
   35    2   20    2.40 4214.20 -0.1956E+03  0.2203E+03
   44  -13   -2    2.40 4443.53 -0.2062E+03  0.1943E+03
    7  -44    3    2.40 4641.40 -0.2154E+03  0.2473E+03
   27  -45    7    2.40 5619.31 -0.2608E+03  0.2807E+03
    7  -44    2    2.40 4759.15 -0.2209E+03  0.2019E+03
   32  -33   29    2.40 4272.16 -0.1983E+03  0.2841E+03
   30  -12   39    2.40 6059.45 -0.2812E+03  0.2778E+03
   31  -25   36    2.40 4378.66 -0.2032E+03  0.2902E+03
   44  -25    4    2.40 4309.22 -0.2000E+03  0.2579E+03
   11   13   47    2.40 4447.76 -0.2064E+03  0.2424E+03
   12   -9   54    2.40 6441.91 -0.2990E+03  0.5382E+03
    6  -37   33    2.40 4305.19 -0.1998E+03  0.3677E+03
   21  -39   30    2.40 4351.84 -0.2020E+03  0.2777E+03
   19   13  -46    2.40 4808.96 -0.2232E+03  0.2315E+03
    7   13  -54    2.40 4211.75 -0.1955E+03  0.2479E+03
    1   40  -14    2.40 5991.74 -0.2781E+03  0.2656E+03
    7    2   55    2.40 4562.21 -0.2118E+03  0.3115E+03
   43  -28    7    2.40 4298.40 -0.1995E+03  0.2547E+03
   29  -39   24    2.40 4298.57 -0.1995E+03  0.2999E+03
    7  -27   47    2.41 5261.65 -0.2442E+03  0.3672E+03
   23  -30   40    2.41 4561.26 -0.2117E+03  0.3066E+03
   41  -22  -27    2.41 4254.29 -0.1975E+03  0.2203E+03
   12  -30   44    2.41 4652.26 -0.2159E+03  0.4882E+03
   39  -34   14    2.41 4326.16 -0.2008E+03  0.2524E+03
   43  -13    7    2.41 4299.68 -0.1996E+03  0.2359E+03
   25   12  -39    2.41 4425.48 -0.2054E+03  0.2130E+03
    8  -27   47    2.41 7086.74 -0.3289E+03  0.6319E+03
   33  -42    9    2.41 5348.82 -0.2483E+03  0.3305E+03
    3   13  -55    2.41 5108.15 -0.2371E+03  0.2831E+03
    7  -43   14    2.41 4356.03 -0.2022E+03  0.2732E+03
   35    9    0    2.41 4304.14 -0.1998E+03  0.1872E+03
   16   31   -5    2.41 4233.80 -0.1965E+03  0.2928E+03
    1    7  -57    2.41 4451.91 -0.2066E+03  0.5443E+03
    2   -1   57    2.41 5453.39 -0.2531E+03  0.2847E+03
    4  -23   50    2.41 5340.21 -0.2479E+03  0.3783E+03
   10   -1  -57    2.41 4291.17 -0.1992E+03  0.2768E+03
   37   -3   20    2.41 5099.25 -0.2367E+03  0.3317E+03
   11   -3   54    2.41 4381.08 -0.2033E+03  0.4027E+03
   21  -15   48    2.41 5728.02 -0.2659E+03  0.3687E+03
   44  -18    4    2.41 5060.50 -0.2349E+03  0.3315E+03
   42   -7    3    2.41 5781.18 -0.2683E+03  0.2392E+03
   16  -42   24    2.41 6182.77 -0.2870E+03  0.4123E+03
   10   -2  -57    2.41 4469.70 -0.2075E+03  0.2849E+03
    6  -20   52    2.41 4788.40 -0.2222E+03  0.4685E+03
   24  -44   15    2.41 4683.48 -0.2174E+03  0.3507E+03
   39  -11   21    2.41 4930.12 -0.2288E+03  0.2688E+03
   37  -40    1    2.41 4243.86 -0.1970E+03  0.2370E+03
   27   -2  -47    2.41 5028.25 -0.2334E+03  0.2326E+03
    5  -23   50    2.41 4398.73 -0.2042E+03  0.3737E+03
   30  -22   38    2.41 4479.94 -0.2079E+03  0.2785E+03
   25   14  -36    2.41 4158.06 -0.1930E+03  0.1957E+03
   15    2   50    2.41 5033.48 -0.2336E+03  0.2623E+03
    9  -13  -55    2.41 5912.35 -0.2744E+03  0.3508E+03
   42   -7  -14    2.41 5966.51 -0.2769E+03  0.2907E+03
    7   30   29    2.41 4808.04 -0.2232E+03  0.2964E+03
   24   -8   45    2.41 4266.77 -0.1980E+03  0.2622E+03
    6  -18   53    2.41 4823.90 -0.2239E+03  0.4395E+03
   26   -1   40    2.41 4374.33 -0.2030E+03  0.2260E+03
    6  -23   50    2.41 5035.25 -0.2337E+03  0.6150E+03
    9  -17   53    2.41 4196.37 -0.1948E+03  0.2501E+03
    9   -5   55    2.41 4816.91 -0.2236E+03  0.4629E+03
   39  -22   23    2.41 4348.46 -0.2018E+03  0.2492E+03
   23    5   40    2.41 4743.55 -0.2202E+03  0.2514E+03
   43  -22   10    2.41 7523.50 -0.3492E+03  0.3543E+03
   15  -17   51    2.41 4160.37 -0.1931E+03  0.3276E+03
   13   14  -50    2.41 4562.12 -0.2117E+03  0.2811E+03
   11   -4   54    2.41 4601.59 -0.2136E+03  0.3210E+03
   38    1    9    2.41 4573.61 -0.2123E+03  0.2280E+03
   41  -26   16    2.42 4679.08 -0.2172E+03  0.3114E+03
    3  -18   53    2.42 6613.57 -0.3070E+03  0.6014E+03
   29  -41   19    2.42 4844.89 -0.2249E+03  0.2884E+03
   19   12   39    2.42 5464.82 -0.2536E+03  0.2514E+03
    2  -35   34    2.42 4907.60 -0.2278E+03  0.2246E+03
   13  -23   49    2.42 4736.23 -0.2198E+03  0.2985E+03
   17  -24   47    2.42 5089.64 -0.2362E+03  0.3635E+03
   33  -36   23    2.42 5047.36 -0.2343E+03  0.3121E+03
   28  -41   20    2.42 4243.68 -0.1970E+03  0.2662E+03
   41   -6    7    2.42 4158.32 -0.1930E+03  0.2277E+03
   22  -29   41    2.42 4440.15 -0.2061E+03  0.3063E+03
   24  -21   44    2.42 4970.77 -0.2307E+03  0.3733E+03
   19   20   28    2.42 4836.16 -0.2245E+03  0.1977E+03
   30  -44   -5    2.42 5349.34 -0.2483E+03  0.2322E+03
   32  -10  -43    2.42 4925.94 -0.2286E+03  0.2056E+03
   37  -25   26    2.42 5623.49 -0.2610E+03  0.2869E+03
   13   -6   53    2.42 6749.19 -0.3133E+03  0.4490E+03
    8   -3  -57    2.42 5084.63 -0.2360E+03  0.2932E+03
   21  -41   25    2.42 4853.70 -0.2253E+03  0.3500E+03
    3   17   49    2.42 6651.10 -0.3087E+03  0.3274E+03
    6   35  -24    2.42 4159.90 -0.1931E+03  0.2794E+03
   15  -25   47    2.42 5449.38 -0.2529E+03  0.3116E+03
    8   -1  -57    2.42 5598.63 -0.2599E+03  0.3417E+03
   19  -27   44    2.42 6701.05 -0.3110E+03  0.3613E+03
   12  -33   40    2.42 5331.53 -0.2475E+03  0.4032E+03
   13   -9   53    2.42 4608.73 -0.2139E+03  0.3123E+03
    8  -37   33    2.42 4527.87 -0.2102E+03  0.2638E+03
   42  -31  -13    2.42 4932.58 -0.2289E+03  0.2225E+03
   16   -1   50    2.42 4473.99 -0.2077E+03  0.2714E+03
   28  -14   41    2.42 4195.83 -0.1947E+03  0.2621E+03
    7   -3  -57    2.42 5376.80 -0.2496E+03  0.5232E+03
   14  -32   41    2.42 4436.55 -0.2059E+03  0.4920E+03
   33  -37   21    2.42 4514.14 -0.2095E+03  0.3164E+03
    0   17  -53    2.43 4441.24 -0.2061E+03  0.2961E+03
    6    2  -57    2.43 4728.01 -0.2194E+03  0.3304E+03
    5  -21   51    2.43 4242.33 -0.1969E+03  0.5961E+03
   43  -28    4    2.43 4640.13 -0.2154E+03  0.2688E+03
   43  -21    9    2.43 4407.01 -0.2045E+03  0.3084E+03
   12  -41   25    2.43 4840.95 -0.2247E+03  0.2810E+03
    3   -2  -57    2.43 6235.22 -0.2894E+03  0.3771E+03
   10  -12  -55    2.43 5837.60 -0.2709E+03  0.3510E+03
   30  -35   28    2.43 7013.43 -0.3255E+03  0.5460E+03
    7  -10   55    2.43 5387.14 -0.2500E+03  0.3024E+03
   27  -35   31    2.43 4831.40 -0.2242E+03  0.2989E+03
    6   -2  -57    2.43 6802.01 -0.3157E+03  0.5502E+03
    6  -25   48    2.43 4392.38 -0.2039E+03  0.3757E+03
    7  -22  -49    2.43 4450.16 -0.2066E+03  0.2728E+03
   30   13   13    2.43 4359.57 -0.2023E+03  0.2257E+03
    7  -19   52    2.43 4545.87 -0.2110E+03  0.4131E+03
   26   -4   41    2.43 5445.31 -0.2527E+03  0.2719E+03
   33  -29   30    2.43 4228.01 -0.1962E+03  0.2776E+03
   21  -42   22    2.43 5042.86 -0.2341E+03  0.3600E+03
    5  -26   47    2.43 5844.82 -0.2713E+03  0.3078E+03
   32   10   13    2.43 5878.29 -0.2728E+03  0.2467E+03
    6   -1  -57    2.43 5252.05 -0.2438E+03  0.5185E+03
   21  -44   15    2.43 5409.26 -0.2511E+03  0.3427E+03
   22  -41   24    2.43 6090.45 -0.2827E+03  0.3277E+03
   39  -13   21    2.43 4274.26 -0.1984E+03  0.2539E+03
   18   19   31    2.43 4671.82 -0.2168E+03  0.2078E+03
   33   -6   31    2.43 5009.89 -0.2325E+03  0.2580E+03
    2   16   50    2.43 5504.11 -0.2555E+03  0.2404E+03
   37  -29   23    2.43 5359.25 -0.2487E+03  0.2583E+03
   18  -20   48    2.43 5168.68 -0.2399E+03  0.3310E+03
    2   16  -53    2.43 4776.01 -0.2217E+03  0.2773E+03
   34  -35   22    2.43 4903.22 -0.2276E+03  0.2735E+03
   26  -32   35    2.43 5632.07 -0.2614E+03  0.3202E+03
    2   -6  -56    2.43 7010.26 -0.3254E+03  0.2980E+03
   15   -7  -55    2.44 4924.35 -0.2286E+03  0.2586E+03
    3   20  -50    2.44 4300.95 -0.1996E+03  0.2793E+03
    3  -12   55    2.44 4700.07 -0.2181E+03  0.5284E+03
    9   24   36    2.44 4895.18 -0.2272E+03  0.3266E+03
   40  -27   17    2.44 4608.46 -0.2139E+03  0.3029E+03
   19  -20  -50    2.44 4617.86 -0.2143E+03  0.2525E+03
   39  -28   19    2.44 6695.89 -0.3108E+03  0.4352E+03
   11    7   50    2.44 4193.65 -0.1946E+03  0.2424E+03
    3    8  -56    2.44 4190.45 -0.1945E+03  0.3323E+03
    4   -4   56    2.44 6302.46 -0.2925E+03  0.3108E+03
   11  -31   42    2.44 5054.25 -0.2346E+03  0.6682E+03
   29  -27   36    2.44 4660.58 -0.2163E+03  0.2783E+03
   19  -44   15    2.44 5197.40 -0.2412E+03  0.3750E+03
   10   -5   54    2.44 7251.52 -0.3366E+03  0.4274E+03
   12  -30   43    2.44 4457.01 -0.2069E+03  0.4656E+03
    1   30   35    2.44 4702.70 -0.2183E+03  0.2639E+03
    3   29  -40    2.44 4940.45 -0.2293E+03  0.3252E+03
   20   -1  -52    2.44 6165.94 -0.2862E+03  0.3127E+03
   15   23  -37    2.44 4281.81 -0.1987E+03  0.3078E+03
   15   30  -18    2.44 5266.00 -0.2444E+03  0.2172E+03
   17  -43   19    2.44 5279.78 -0.2451E+03  0.4023E+03
   42   -8    1    2.44 5077.69 -0.2357E+03  0.2269E+03
   15  -41   25    2.44 5094.63 -0.2365E+03  0.3943E+03
    7   -8   55    2.44 5208.06 -0.2417E+03  0.2998E+03
   20  -19   47    2.44 5342.92 -0.2480E+03  0.3267E+03
    3   17  -52    2.44 6150.77 -0.2855E+03  0.3309E+03
    9  -30   43    2.44 4818.00 -0.2236E+03  0.3691E+03
    5   16  -52    2.44 5409.60 -0.2511E+03  0.2694E+03
   41  -15  -25    2.44 4458.29 -0.2069E+03  0.1973E+03
   34  -36   20    2.44 4224.73 -0.1961E+03  0.2577E+03
   21   19  -34    2.44 4769.73 -0.2214E+03  0.2152E+03
   12  -44   -8    2.44 4330.79 -0.2010E+03  0.2234E+03
    4   27  -42    2.44 4556.95 -0.2115E+03  0.3027E+03
   37  -25   25    2.44 4271.81 -0.1983E+03  0.2748E+03
   41  -10   11    2.44 4346.31 -0.2017E+03  0.2051E+03
   21  -38   30    2.44 7668.61 -0.3559E+03  0.4176E+03
   14  -23   48    2.44 5627.09 -0.2612E+03  0.2842E+03
    4  -30   42    2.44 4408.16 -0.2046E+03  0.2776E+03
   11   -2   53    2.44 4561.42 -0.2117E+03  0.2923E+03
    7   -7  -56    2.44 4170.74 -0.1936E+03  0.3025E+03
   25  -14  -49    2.44 4677.58 -0.2171E+03  0.2639E+03
   21  -18  -50    2.45 4884.72 -0.2267E+03  0.2407E+03
    6    9  -55    2.45 6868.48 -0.3188E+03  0.3369E+03
   17   -7   50    2.45 4269.15 -0.1981E+03  0.3235E+03
   11   19   40    2.45 4465.41 -0.2073E+03  0.2124E+03
   33   -9   32    2.45 5250.25 -0.2437E+03  0.2503E+03
   20   -4   47    2.45 5041.42 -0.2340E+03  0.2724E+03
   27   -6   40    2.45 6284.93 -0.2917E+03  0.2858E+03
   38  -11   22    2.45 5051.12 -0.2344E+03  0.2864E+03
    8    0   54    2.45 4185.45 -0.1943E+03  0.3247E+03
   37    2    9    2.45 5741.34 -0.2665E+03  0.2294E+03
   14    4   49    2.45 6000.29 -0.2785E+03  0.3100E+03
    3   24  -46    2.45 4728.60 -0.2195E+03  0.2440E+03
   15  -43   18    2.45 4840.21 -0.2247E+03  0.2654E+03
   19   11   39    2.45 4901.77 -0.2275E+03  0.3284E+03
   22    4   41    2.45 4884.91 -0.2267E+03  0.3077E+03
   38  -18   24    2.45 5228.92 -0.2427E+03  0.2380E+03
   33  -21   33    2.45 4604.22 -0.2137E+03  0.2741E+03
   19  -45    7    2.45 4281.51 -0.1987E+03  0.3062E+03
   37   -4   19    2.45 4704.45 -0.2184E+03  0.2336E+03
   33  -42   -3    2.45 4346.90 -0.2018E+03  0.2821E+03
   10   11   48    2.45 4278.97 -0.1986E+03  0.3014E+03
   15  -37   33    2.45 4412.49 -0.2048E+03  0.3405E+03
    2   17  -52    2.45 5002.07 -0.2322E+03  0.2785E+03
   18   18  -40    2.45 4315.16 -0.2003E+03  0.3092E+03
   11   18   41    2.45 6381.66 -0.2962E+03  0.3369E+03
   20  -25   44    2.45 5092.46 -0.2364E+03  0.3089E+03
    3   30  -38    2.45 5001.38 -0.2321E+03  0.3069E+03
   20  -18   47    2.45 6189.16 -0.2873E+03  0.3661E+03
   18  -45   -5    2.45 4298.40 -0.1995E+03  0.2577E+03
    4  -29   43    2.45 7839.19 -0.3638E+03  0.4310E+03
   30   -7   36    2.45 6131.34 -0.2846E+03  0.2768E+03
   17  -11   50    2.45 5859.67 -0.2720E+03  0.3727E+03
   39  -23  -29    2.45 6446.97 -0.2992E+03  0.2419E+03
   36  -24   27    2.45 4536.04 -0.2105E+03  0.2620E+03
   13   24   30    2.45 6128.54 -0.2845E+03  0.2297E+03
   21  -19   46    2.45 4905.31 -0.2277E+03  0.3333E+03
    4    6  -56    2.45 7437.28 -0.3452E+03  0.3981E+03
   15   31   -6    2.45 4330.70 -0.2010E+03  0.1883E+03
   24    4  -46    2.45 4194.21 -0.1947E+03  0.2337E+03
   23    6   38    2.45 4397.92 -0.2041E+03  0.2905E+03
    9   -4   54    2.45 4557.05 -0.2115E+03  0.3812E+03
   21   17   28    2.45 5140.92 -0.2386E+03  0.2767E+03
   39   -2    7    2.45 4673.52 -0.2169E+03  0.2280E+03
   20  -23   45    2.45 8032.95 -0.3728E+03  0.3707E+03
    3   10  -55    2.45 9239.04 -0.4288E+03  0.4483E+03
   13    7  -53    2.45 6856.39 -0.3182E+03  0.2676E+03
   32  -40   14    2.46 5301.78 -0.2461E+03  0.3243E+03
   42  -18   11    2.46 4685.82 -0.2175E+03  0.2530E+03
   27  -29   36    2.46 4159.99 -0.1931E+03  0.3178E+03
   21    9   38    2.46 5305.64 -0.2463E+03  0.2667E+03
   36  -10   26    2.46 4480.30 -0.2079E+03  0.2755E+03
   11   11  -52    2.46 9337.17 -0.4334E+03  0.4190E+03
   11  -25   47    2.46 5761.66 -0.2674E+03  0.3657E+03
   15  -41   24    2.46 4194.68 -0.1947E+03  0.3882E+03
    6   -5   55    2.46 5080.47 -0.2358E+03  0.3354E+03
    7  -35   35    2.46 5246.55 -0.2435E+03  0.2797E+03
   17  -41   24    2.46 5681.93 -0.2637E+03  0.4107E+03
   41  -29   10    2.46 5814.01 -0.2699E+03  0.2252E+03
   33    4   20    2.46 4347.00 -0.2018E+03  0.2200E+03
   15  -25   46    2.46 5826.87 -0.2704E+03  0.3019E+03
   26  -17   42    2.46 6507.40 -0.3020E+03  0.3186E+03
    8  -10   54    2.46 4862.75 -0.2257E+03  0.2945E+03
    4    5  -56    2.46 4470.52 -0.2075E+03  0.3287E+03
   37  -23   25    2.46 4395.16 -0.2040E+03  0.2900E+03
   28  -21   39    2.46 5483.68 -0.2545E+03  0.2917E+03
   22    0   43    2.46 5315.04 -0.2467E+03  0.2571E+03
   10  -25   47    2.46 4144.66 -0.1924E+03  0.4464E+03
    5   26  -42    2.46 5500.43 -0.2553E+03  0.3102E+03
   10  -40   25    2.46 4563.71 -0.2118E+03  0.2730E+03
   33  -17   33    2.46 4453.61 -0.2067E+03  0.2506E+03
   19  -15   48    2.46 4896.55 -0.2273E+03  0.3484E+03
    4   21  -48    2.46 4323.83 -0.2007E+03  0.2442E+03
    4  -21  -48    2.46 4208.01 -0.1953E+03  0.2661E+03
   37  -33   17    2.46 5591.57 -0.2595E+03  0.3816E+03
    6   -9  -55    2.46 5774.59 -0.2680E+03  0.3669E+03
    8    0  -56    2.46 5316.56 -0.2468E+03  0.3550E+03
    2    5  -56    2.46 5984.69 -0.2778E+03  0.5659E+03
    5   18  -50    2.46 5929.71 -0.2752E+03  0.2714E+03
    2  -41   -1    2.46 5050.28 -0.2344E+03  0.2009E+03
   18    4  -51    2.46 4909.73 -0.2279E+03  0.2912E+03
   13   -7   52    2.46 5240.79 -0.2432E+03  0.4035E+03
   41   -7  -15    2.47 4159.71 -0.1931E+03  0.2429E+03
    7   24  -43    2.47 6747.23 -0.3132E+03  0.3343E+03
   29  -36   26    2.47 5509.79 -0.2557E+03  0.3522E+03
   19    0   46    2.47 5268.29 -0.2445E+03  0.3443E+03
    6   37   -3    2.47 4302.10 -0.1997E+03  0.2221E+03
   15  -36   34    2.47 6455.28 -0.2996E+03  0.6590E+03
    9  -31   41    2.47 4203.89 -0.1951E+03  0.3644E+03
   19    8  -48    2.47 5004.07 -0.2323E+03  0.2209E+03
   32    9   13    2.47 4229.92 -0.1963E+03  0.2389E+03
    0   15  -53    2.47 4730.92 -0.2196E+03  0.3320E+03
    7   -3  -56    2.47 5585.89 -0.2593E+03  0.5211E+03
   17  -37   32    2.47 4593.80 -0.2132E+03  0.6764E+03
   32  -42   -5    2.47 4303.91 -0.1998E+03  0.2750E+03
    4   -3  -56    2.47 5074.78 -0.2355E+03  0.3388E+03
   12  -39   28    2.47 4730.23 -0.2195E+03  0.2894E+03
   10  -31   41    2.47 4361.06 -0.2024E+03  0.3555E+03
   14  -36   34    2.47 4521.37 -0.2099E+03  0.3476E+03
   15    8  -51    2.47 5782.34 -0.2684E+03  0.3179E+03
   10  -26   46    2.47 4880.37 -0.2265E+03  0.4800E+03
   35  -33   21    2.47 4395.97 -0.2040E+03  0.2541E+03
   32    0   27    2.47 4810.76 -0.2233E+03  0.2578E+03
    1   10   53    2.47 4488.47 -0.2083E+03  0.3375E+03
   22    7   38    2.47 4610.30 -0.2140E+03  0.2603E+03
   41  -15  -23    2.47 4851.69 -0.2252E+03  0.2062E+03
   10   31  -26    2.47 5455.89 -0.2532E+03  0.2451E+03
    3  -19   51    2.47 4287.49 -0.1990E+03  0.3500E+03
   16  -20   48    2.47 4264.81 -0.1979E+03  0.3372E+03
   29  -11   38    2.47 4535.12 -0.2105E+03  0.2587E+03
   35  -11   28    2.47 4220.78 -0.1959E+03  0.2304E+03
   12    1   51    2.47 5285.65 -0.2453E+03  0.2557E+03
    6    1  -56    2.47 6012.53 -0.2791E+03  0.3531E+03
   22  -18   45    2.47 6119.30 -0.2840E+03  0.3721E+03
   26  -20   41    2.47 4296.11 -0.1994E+03  0.2495E+03
    8   -5   54    2.47 4762.76 -0.2211E+03  0.2946E+03
   30  -17   37    2.47 4869.18 -0.2260E+03  0.2474E+03
    4  -19   51    2.47 5597.23 -0.2598E+03  0.6044E+03
    4   27  -41    2.47 6497.53 -0.3016E+03  0.3635E+03
    5   -2  -56    2.47 4213.71 -0.1956E+03  0.4116E+03
   43  -18    3    2.47 4528.23 -0.2102E+03  0.2981E+03
   12   12   45    2.47 4382.83 -0.2034E+03  0.2206E+03
   30   11  -26    2.47 4270.64 -0.1982E+03  0.2788E+03
    2    2  -56    2.47 4483.19 -0.2081E+03  0.4064E+03
    3    1   55    2.47 6303.03 -0.2925E+03  0.3899E+03
   18  -41   23    2.47 4954.84 -0.2300E+03  0.3328E+03
   23   -8  -50    2.47 4826.17 -0.2240E+03  0.2859E+03
   10    9  -53    2.47 5341.03 -0.2479E+03  0.3360E+03
   34   -1   23    2.47 4517.23 -0.2097E+03  0.2441E+03
   35  -30   24    2.47 4742.72 -0.2201E+03  0.2470E+03
    9   -8  -55    2.48 5809.71 -0.2697E+03  0.4320E+03
    8   33   16    2.48 4235.80 -0.1966E+03  0.2282E+03
   35  -31   23    2.48 4232.21 -0.1964E+03  0.2583E+03
   14  -37   32    2.48 7687.73 -0.3568E+03  0.4255E+03
    4    1  -56    2.48 4143.08 -0.1923E+03  0.4971E+03
   18  -44   11    2.48 5503.74 -0.2555E+03  0.3372E+03
   31  -39   17    2.48 4210.50 -0.1954E+03  0.3200E+03
   19    9   40    2.48 4462.07 -0.2071E+03  0.2617E+03
   17   19  -39    2.48 4673.52 -0.2169E+03  0.3328E+03
   18   20  -36    2.48 4980.34 -0.2312E+03  0.2186E+03
    9  -14  -53    2.48 5203.06 -0.2415E+03  0.3493E+03
   18   -1   47    2.48 4733.40 -0.2197E+03  0.2581E+03
   40  -34   -6    2.48 4616.39 -0.2143E+03  0.2117E+03
   37    2    6    2.48 4177.59 -0.1939E+03  0.2041E+03
   33  -29   28    2.48 4234.20 -0.1965E+03  0.2636E+03
   24  -36   30    2.48 4767.26 -0.2213E+03  0.2832E+03
   14  -33   38    2.48 5195.19 -0.2411E+03  0.4754E+03
   32  -39   15    2.48 6059.85 -0.2813E+03  0.3037E+03
   43  -15   -2    2.48 7854.91 -0.3646E+03  0.3992E+03
   24  -42   17    2.48 5649.93 -0.2622E+03  0.3074E+03
   39   -4    9    2.48 5091.55 -0.2363E+03  0.2417E+03
    2   31  -36    2.48 5310.28 -0.2465E+03  0.3249E+03
    4   -3   55    2.48 5521.24 -0.2563E+03  0.2935E+03
   13  -14   51    2.48 5145.03 -0.2388E+03  0.3231E+03
   37   -3   16    2.48 5497.91 -0.2552E+03  0.2393E+03
   33  -41    3    2.48 4575.81 -0.2124E+03  0.2838E+03
    5   30   29    2.48 4737.04 -0.2199E+03  0.1971E+03
    4   18   46    2.48 5026.07 -0.2333E+03  0.3101E+03
   31  -38   19    2.48 5493.37 -0.2550E+03  0.3454E+03
   32   12   -9    2.48 4210.95 -0.1954E+03  0.2083E+03
    7  -11  -54    2.48 5849.13 -0.2715E+03  0.3412E+03
   13   17   39    2.48 4700.30 -0.2182E+03  0.2269E+03
   34    8    4    2.48 4177.62 -0.1939E+03  0.2313E+03
    7   11  -53    2.48 4812.32 -0.2234E+03  0.3824E+03
    5  -30   41    2.48 9672.85 -0.4490E+03  0.4865E+03
   41  -22   13    2.48 4728.82 -0.2195E+03  0.2526E+03
   14  -35   35    2.48 6069.49 -0.2817E+03  0.4865E+03
    3   -1   55    2.49 6445.52 -0.2992E+03  0.3046E+03
   24   11   30    2.49 7671.55 -0.3561E+03  0.3775E+03
   32   -3   29    2.49 5098.19 -0.2366E+03  0.2663E+03
   20  -33   36    2.49 4673.28 -0.2169E+03  0.2609E+03
    0   12   52    2.49 6162.91 -0.2860E+03  0.3847E+03
   36   -1  -27    2.49 5419.35 -0.2515E+03  0.2136E+03
   31  -35   24    2.49 4426.13 -0.2054E+03  0.3177E+03
   18  -37   31    2.49 5462.29 -0.2535E+03  0.6224E+03
   14  -44   -4    2.49 4150.78 -0.1927E+03  0.2630E+03
   43  -24   -7    2.49 7166.06 -0.3326E+03  0.2500E+03
    7   25  -41    2.49 5275.18 -0.2448E+03  0.3163E+03
   17  -40   25    2.49 4846.68 -0.2250E+03  0.3961E+03
   18  -31   39    2.49 4926.65 -0.2287E+03  0.2585E+03
   31   13  -12    2.49 5094.39 -0.2365E+03  0.2886E+03
    4  -24   47    2.49 4803.80 -0.2230E+03  0.2864E+03
   30  -37   22    2.49 4187.13 -0.1943E+03  0.3430E+03
    1    2   55    2.49 4373.42 -0.2030E+03  0.2689E+03
   19  -43   15    2.49 4343.47 -0.2016E+03  0.3274E+03
    8   21  -45    2.49 5933.70 -0.2754E+03  0.3091E+03
   14  -41   22    2.49 4277.57 -0.1985E+03  0.2973E+03
    3   -4   55    2.49 4282.02 -0.1987E+03  0.2739E+03
   19   21   23    2.49 5716.02 -0.2653E+03  0.3029E+03
   17  -24   45    2.49 4428.03 -0.2055E+03  0.3281E+03
    1   36  -25    2.49 4692.99 -0.2178E+03  0.2042E+03
   18  -39   27    2.49 5020.05 -0.2330E+03  0.4377E+03
   23   17   22    2.49 4682.86 -0.2174E+03  0.2025E+03
    0    3   55    2.49 4317.11 -0.2004E+03  0.2671E+03
    7    5   52    2.49 6358.68 -0.2951E+03  0.3322E+03
   19  -34   35    2.49 5756.23 -0.2672E+03  0.2683E+03
   37  -29   20    2.49 5388.11 -0.2501E+03  0.2626E+03
   36  -32   19    2.49 4270.83 -0.1982E+03  0.2363E+03
   18  -41   22    2.49 4944.67 -0.2295E+03  0.3473E+03
    3  -39   20    2.49 6174.57 -0.2866E+03  0.2654E+03
   22   19   20    2.49 5598.01 -0.2598E+03  0.2396E+03
    9  -43    4    2.49 5065.70 -0.2351E+03  0.3051E+03
    6   -8   54    2.49 5373.59 -0.2494E+03  0.3045E+03
   24  -39   24    2.49 4179.10 -0.1940E+03  0.2860E+03
   30   -8   35    2.49 4462.50 -0.2071E+03  0.2458E+03
   11    4   50    2.49 5586.83 -0.2593E+03  0.2584E+03
   31  -22   34    2.49 5012.92 -0.2327E+03  0.2596E+03
   15    4   47    2.49 6073.35 -0.2819E+03  0.3370E+03
   26   14  -30    2.50 4673.62 -0.2169E+03  0.2117E+03
   11  -22   48    2.50 4362.17 -0.2025E+03  0.2541E+03
   22  -24   42    2.50 4457.90 -0.2069E+03  0.3294E+03
   16  -39   27    2.50 4162.14 -0.1932E+03  0.3935E+03
   11    9  -52    2.50 4365.96 -0.2026E+03  0.2130E+03
   20    8   39    2.50 5728.47 -0.2659E+03  0.2761E+03
   11   31  -22    2.50 4244.06 -0.1970E+03  0.2180E+03
    2   23  -46    2.50 4461.32 -0.2071E+03  0.2407E+03
   17  -35   34    2.50 6779.19 -0.3147E+03  0.3926E+03
   23  -32   35    2.50 5119.02 -0.2376E+03  0.3396E+03
    1  -23   47    2.50 5779.41 -0.2682E+03  0.3461E+03
    5   33   21    2.50 4510.98 -0.2094E+03  0.2479E+03
    7   33  -24    2.50 4600.80 -0.2135E+03  0.3228E+03
   13  -34   36    2.50 4147.71 -0.1925E+03  0.6081E+03
   25  -11   42    2.50 4592.18 -0.2131E+03  0.2611E+03
    9   -6   53    2.50 4964.80 -0.2304E+03  0.3748E+03
   14  -37   31    2.50 4248.17 -0.1972E+03  0.3423E+03
   11  -20   49    2.50 4279.86 -0.1986E+03  0.2612E+03
   30  -18   36    2.50 5237.28 -0.2431E+03  0.2577E+03
   24  -15   43    2.50 4312.58 -0.2002E+03  0.3057E+03
   11  -26   45    2.50 6122.60 -0.2842E+03  0.4186E+03
   39  -13  -27    2.50 4381.56 -0.2034E+03  0.2221E+03
    5   37   -5    2.50 4954.77 -0.2300E+03  0.2024E+03
   10  -15  -52    2.50 4263.68 -0.1979E+03  0.2911E+03
    9   -3  -55    2.50 5954.08 -0.2764E+03  0.3532E+03
   33   -4   27    2.50 5038.84 -0.2339E+03  0.2406E+03
    7  -25   46    2.50 5765.40 -0.2676E+03  0.6061E+03
    8  -25   46    2.50 5910.26 -0.2743E+03  0.7176E+03
    1   -1   55    2.50 4767.43 -0.2213E+03  0.2583E+03
   25  -21  -45    2.50 4763.53 -0.2211E+03  0.2513E+03
   22   10   34    2.50 4396.21 -0.2040E+03  0.2342E+03
   33   10   -4    2.50 4368.11 -0.2027E+03  0.1770E+03
    8  -19   50    2.50 5565.30 -0.2583E+03  0.3277E+03
   24    6  -43    2.50 4688.96 -0.2176E+03  0.2145E+03
   21  -40   23    2.50 5633.46 -0.2615E+03  0.2982E+03
   17  -42   18    2.51 5512.03 -0.2558E+03  0.4117E+03
   11   -6   52    2.51 4396.33 -0.2041E+03  0.2995E+03
   34  -36   16    2.51 4663.47 -0.2165E+03  0.2474E+03
    2   31  -35    2.51 5275.68 -0.2449E+03  0.3199E+03
    5   11  -53    2.51 4897.54 -0.2273E+03  0.2615E+03
   17  -19   47    2.51 4309.53 -0.2000E+03  0.3122E+03
    3  -22   48    2.51 5051.94 -0.2345E+03  0.2921E+03
   12   19   37    2.51 4662.51 -0.2164E+03  0.2216E+03
   12    4   49    2.51 5642.61 -0.2619E+03  0.2579E+03
   40  -22   15    2.51 4880.86 -0.2265E+03  0.2428E+03
   19  -14   47    2.51 4975.15 -0.2309E+03  0.3632E+03
   29   15  -14    2.51 5079.12 -0.2357E+03  0.2141E+03
   13   21  -40    2.51 4177.61 -0.1939E+03  0.2154E+03
    5   -4  -55    2.51 4190.69 -0.1945E+03  0.3100E+03
   25  -36   28    2.51 8335.45 -0.3869E+03  0.5080E+03
    8   -2  -55    2.51 5068.13 -0.2352E+03  0.3276E+03
   13  -43   11    2.51 4725.50 -0.2193E+03  0.2447E+03
   30  -24   34    2.51 4359.46 -0.2023E+03  0.2745E+03
   37  -29   19    2.51 4165.04 -0.1933E+03  0.3151E+03
   38  -17   21    2.51 5499.89 -0.2553E+03  0.2612E+03
   17  -41   21    2.51 4864.76 -0.2258E+03  0.4323E+03
    0   27   38    2.51 4579.57 -0.2126E+03  0.2488E+03
    9    8   49    2.51 4974.02 -0.2309E+03  0.2478E+03
   30   13    6    2.51 5635.61 -0.2616E+03  0.2929E+03
   35  -32   20    2.51 4323.83 -0.2007E+03  0.2484E+03
    5   -3   54    2.51 4317.27 -0.2004E+03  0.3175E+03
   19  -44    5    2.51 8059.22 -0.3741E+03  0.3864E+03
   11  -14   51    2.51 4383.67 -0.2035E+03  0.3636E+03
    2    4  -55    2.51 4396.06 -0.2040E+03  0.4772E+03
    6   -3  -55    2.51 6618.58 -0.3072E+03  0.5068E+03
   41  -30    0    2.51 4173.00 -0.1937E+03  0.2802E+03
   34  -30   24    2.51 4683.37 -0.2174E+03  0.2479E+03
   24  -39   23    2.51 5066.32 -0.2351E+03  0.2949E+03
   29  -17   37    2.51 4248.26 -0.1972E+03  0.2584E+03
    2   19  -49    2.51 4794.88 -0.2226E+03  0.2482E+03
   28  -39   19    2.51 4670.78 -0.2168E+03  0.2696E+03
    3  -23   47    2.51 4175.70 -0.1938E+03  0.2691E+03
   21   -7   45    2.52 4750.91 -0.2205E+03  0.2839E+03
   19  -44    4    2.52 4808.40 -0.2232E+03  0.3378E+03
   21   11  -42    2.52 4896.50 -0.2273E+03  0.2375E+03
    8   23   36    2.52 4280.38 -0.1987E+03  0.1951E+03
   13  -25   45    2.52 4197.27 -0.1948E+03  0.2565E+03
   39  -18   18    2.52 5291.40 -0.2456E+03  0.2704E+03
   30    7   21    2.52 4422.76 -0.2053E+03  0.2858E+03
   15  -28   42    2.52 6688.33 -0.3104E+03  0.3192E+03
   40  -25   13    2.52 4291.93 -0.1992E+03  0.3117E+03
    1  -32   35    2.52 4810.09 -0.2233E+03  0.2231E+03
    0    9  -54    2.52 4228.23 -0.1962E+03  0.5036E+03
    3   -1  -55    2.52 4787.97 -0.2222E+03  0.3318E+03
   20  -37   29    2.52 4773.62 -0.2216E+03  0.2724E+03
   17    5   44    2.52 7526.43 -0.3493E+03  0.2945E+03
   22  -37   28    2.52 4221.22 -0.1959E+03  0.3953E+03
   18  -44    2    2.52 4760.91 -0.2210E+03  0.2812E+03
   23  -28   38    2.52 4374.06 -0.2030E+03  0.2917E+03
   32  -31   26    2.52 4842.80 -0.2248E+03  0.2488E+03
   12   -4   51    2.52 4697.11 -0.2180E+03  0.4924E+03
   34  -39    5    2.52 5215.65 -0.2421E+03  0.2549E+03
   36  -35   12    2.52 5324.85 -0.2471E+03  0.2653E+03
   11   -1   51    2.52 6067.62 -0.2816E+03  0.3510E+03
    8   18  -47    2.52 6173.98 -0.2866E+03  0.3624E+03
   34  -35   17    2.52 4149.60 -0.1926E+03  0.2542E+03
   42  -17    4    2.52 4877.01 -0.2264E+03  0.2258E+03
    9   -1   52    2.52 4495.87 -0.2087E+03  0.3612E+03
   30   14   -5    2.52 5545.05 -0.2574E+03  0.2065E+03
   11   -6  -54    2.52 5408.61 -0.2510E+03  0.3179E+03
    8   19  -46    2.52 4556.93 -0.2115E+03  0.3361E+03
   37  -35    8    2.53 5960.48 -0.2767E+03  0.2694E+03
    3  -20   49    2.53 4237.27 -0.1967E+03  0.3413E+03

 NUMBER OF UNIQUE ALIEN REFLECTIONS WITH A Z-SCORE ABOVE LIMIT     90718
 (ALIENS ABOVE LIMIT (REJECT_ALIEN=      20.0) ARE MARKED INVALID)

 NUMBER OF REFLECTION RECORDS ON OUTPUT FILE "XDS_ASCII.HKL"      818061
 NUMBER OF ACCEPTED OBSERVATIONS (INCLUDING SYSTEMATIC ABSENCES)  662280
 NUMBER OF REJECTED MISFITS & ALIENS (marked by -1*SIGMA(IOBS))   155781


 cpu time used                 34.5 sec
 elapsed wall-clock time       23.7 sec
