package io.github.darthakiranihil.konna.core.graphics.render;

import java.nio.*;


public interface KGl20 extends KGl {

    // GL 1.0 Constants
    int
        GL_ACCUM  = 0x100,
        GL_LOAD   = 0x101,
        GL_RETURN = 0x102,
        GL_MULT   = 0x103,
        GL_ADD    = 0x104;

    int
        GL_NEVER    = 0x200,
        GL_LESS     = 0x201,
        GL_EQUAL    = 0x202,
        GL_LEQUAL   = 0x203,
        GL_GREATER  = 0x204,
        GL_NOTEQUAL = 0x205,
        GL_GEQUAL   = 0x206,
        GL_ALWAYS   = 0x207;

    int
        GL_CURRENT_BIT         = 0x1,
        GL_POINT_BIT           = 0x2,
        GL_LINE_BIT            = 0x4,
        GL_POLYGON_BIT         = 0x8,
        GL_POLYGON_STIPPLE_BIT = 0x10,
        GL_PIXEL_MODE_BIT      = 0x20,
        GL_LIGHTING_BIT        = 0x40,
        GL_FOG_BIT             = 0x80,
        GL_DEPTH_BUFFER_BIT    = 0x100,
        GL_ACCUM_BUFFER_BIT    = 0x200,
        GL_STENCIL_BUFFER_BIT  = 0x400,
        GL_VIEWPORT_BIT        = 0x800,
        GL_TRANSFORM_BIT       = 0x1000,
        GL_ENABLE_BIT          = 0x2000,
        GL_COLOR_BUFFER_BIT    = 0x4000,
        GL_HINT_BIT            = 0x8000,
        GL_EVAL_BIT            = 0x10000,
        GL_LIST_BIT            = 0x20000,
        GL_TEXTURE_BIT         = 0x40000,
        GL_SCISSOR_BIT         = 0x80000,
        GL_ALL_ATTRIB_BITS     = 0xFFFFF;

    int
        GL_POINTS         = 0x0,
        GL_LINES          = 0x1,
        GL_LINE_LOOP      = 0x2,
        GL_LINE_STRIP     = 0x3,
        GL_TRIANGLES      = 0x4,
        GL_TRIANGLE_STRIP = 0x5,
        GL_TRIANGLE_FAN   = 0x6,
        GL_QUADS          = 0x7,
        GL_QUAD_STRIP     = 0x8,
        GL_POLYGON        = 0x9;

    int
        GL_ZERO                = 0,
        GL_ONE                 = 1,
        GL_SRC_COLOR           = 0x300,
        GL_ONE_MINUS_SRC_COLOR = 0x301,
        GL_SRC_ALPHA           = 0x302,
        GL_ONE_MINUS_SRC_ALPHA = 0x303,
        GL_DST_ALPHA           = 0x304,
        GL_ONE_MINUS_DST_ALPHA = 0x305;

    int
        GL_DST_COLOR           = 0x306,
        GL_ONE_MINUS_DST_COLOR = 0x307,
        GL_SRC_ALPHA_SATURATE  = 0x308;

    int
        GL_TRUE  = 1,
        GL_FALSE = 0;

    int
        GL_CLIP_PLANE0 = 0x3000,
        GL_CLIP_PLANE1 = 0x3001,
        GL_CLIP_PLANE2 = 0x3002,
        GL_CLIP_PLANE3 = 0x3003,
        GL_CLIP_PLANE4 = 0x3004,
        GL_CLIP_PLANE5 = 0x3005;

    int
        GL_BYTE           = 0x1400,
        GL_UNSIGNED_BYTE  = 0x1401,
        GL_SHORT          = 0x1402,
        GL_UNSIGNED_SHORT = 0x1403,
        GL_INT            = 0x1404,
        GL_UNSIGNED_INT   = 0x1405,
        GL_FLOAT          = 0x1406,
        GL_2_BYTES        = 0x1407,
        GL_3_BYTES        = 0x1408,
        GL_4_BYTES        = 0x1409,
        GL_DOUBLE         = 0x140A;

    int
        GL_NONE           = 0,
        GL_FRONT_LEFT     = 0x400,
        GL_FRONT_RIGHT    = 0x401,
        GL_BACK_LEFT      = 0x402,
        GL_BACK_RIGHT     = 0x403,
        GL_FRONT          = 0x404,
        GL_BACK           = 0x405,
        GL_LEFT           = 0x406,
        GL_RIGHT          = 0x407,
        GL_FRONT_AND_BACK = 0x408,
        GL_AUX0           = 0x409,
        GL_AUX1           = 0x40A,
        GL_AUX2           = 0x40B,
        GL_AUX3           = 0x40C;

    int
        GL_NO_ERROR          = 0,
        GL_INVALID_ENUM      = 0x500,
        GL_INVALID_VALUE     = 0x501,
        GL_INVALID_OPERATION = 0x502,
        GL_STACK_OVERFLOW    = 0x503,
        GL_STACK_UNDERFLOW   = 0x504,
        GL_OUT_OF_MEMORY     = 0x505;

    int
        GL_2D               = 0x600,
        GL_3D               = 0x601,
        GL_3D_COLOR         = 0x602,
        GL_3D_COLOR_TEXTURE = 0x603,
        GL_4D_COLOR_TEXTURE = 0x604;

    int
        GL_PASS_THROUGH_TOKEN = 0x700,
        GL_POINT_TOKEN        = 0x701,
        GL_LINE_TOKEN         = 0x702,
        GL_POLYGON_TOKEN      = 0x703,
        GL_BITMAP_TOKEN       = 0x704,
        GL_DRAW_PIXEL_TOKEN   = 0x705,
        GL_COPY_PIXEL_TOKEN   = 0x706,
        GL_LINE_RESET_TOKEN   = 0x707;

    int
        GL_EXP  = 0x800,
        GL_EXP2 = 0x801;

    int
        GL_CW  = 0x900,
        GL_CCW = 0x901;

    int
        GL_COEFF  = 0xA00,
        GL_ORDER  = 0xA01,
        GL_DOMAIN = 0xA02;

    int
        GL_CURRENT_COLOR                 = 0xB00,
        GL_CURRENT_INDEX                 = 0xB01,
        GL_CURRENT_NORMAL                = 0xB02,
        GL_CURRENT_TEXTURE_COORDS        = 0xB03,
        GL_CURRENT_RASTER_COLOR          = 0xB04,
        GL_CURRENT_RASTER_INDEX          = 0xB05,
        GL_CURRENT_RASTER_TEXTURE_COORDS = 0xB06,
        GL_CURRENT_RASTER_POSITION       = 0xB07,
        GL_CURRENT_RASTER_POSITION_VALID = 0xB08,
        GL_CURRENT_RASTER_DISTANCE       = 0xB09,
        GL_POINT_SMOOTH                  = 0xB10,
        GL_POINT_SIZE                    = 0xB11,
        GL_POINT_SIZE_RANGE              = 0xB12,
        GL_POINT_SIZE_GRANULARITY        = 0xB13,
        GL_LINE_SMOOTH                   = 0xB20,
        GL_LINE_WIDTH                    = 0xB21,
        GL_LINE_WIDTH_RANGE              = 0xB22,
        GL_LINE_WIDTH_GRANULARITY        = 0xB23,
        GL_LINE_STIPPLE                  = 0xB24,
        GL_LINE_STIPPLE_PATTERN          = 0xB25,
        GL_LINE_STIPPLE_REPEAT           = 0xB26,
        GL_LIST_MODE                     = 0xB30,
        GL_MAX_LIST_NESTING              = 0xB31,
        GL_LIST_BASE                     = 0xB32,
        GL_LIST_INDEX                    = 0xB33,
        GL_POLYGON_MODE                  = 0xB40,
        GL_POLYGON_SMOOTH                = 0xB41,
        GL_POLYGON_STIPPLE               = 0xB42,
        GL_EDGE_FLAG                     = 0xB43,
        GL_CULL_FACE                     = 0xB44,
        GL_CULL_FACE_MODE                = 0xB45,
        GL_FRONT_FACE                    = 0xB46,
        GL_LIGHTING                      = 0xB50,
        GL_LIGHT_MODEL_LOCAL_VIEWER      = 0xB51,
        GL_LIGHT_MODEL_TWO_SIDE          = 0xB52,
        GL_LIGHT_MODEL_AMBIENT           = 0xB53,
        GL_SHADE_MODEL                   = 0xB54,
        GL_COLOR_MATERIAL_FACE           = 0xB55,
        GL_COLOR_MATERIAL_PARAMETER      = 0xB56,
        GL_COLOR_MATERIAL                = 0xB57,
        GL_FOG                           = 0xB60,
        GL_FOG_INDEX                     = 0xB61,
        GL_FOG_DENSITY                   = 0xB62,
        GL_FOG_START                     = 0xB63,
        GL_FOG_END                       = 0xB64,
        GL_FOG_MODE                      = 0xB65,
        GL_FOG_COLOR                     = 0xB66,
        GL_DEPTH_RANGE                   = 0xB70,
        GL_DEPTH_TEST                    = 0xB71,
        GL_DEPTH_WRITEMASK               = 0xB72,
        GL_DEPTH_CLEAR_VALUE             = 0xB73,
        GL_DEPTH_FUNC                    = 0xB74,
        GL_ACCUM_CLEAR_VALUE             = 0xB80,
        GL_STENCIL_TEST                  = 0xB90,
        GL_STENCIL_CLEAR_VALUE           = 0xB91,
        GL_STENCIL_FUNC                  = 0xB92,
        GL_STENCIL_VALUE_MASK            = 0xB93,
        GL_STENCIL_FAIL                  = 0xB94,
        GL_STENCIL_PASS_DEPTH_FAIL       = 0xB95,
        GL_STENCIL_PASS_DEPTH_PASS       = 0xB96,
        GL_STENCIL_REF                   = 0xB97,
        GL_STENCIL_WRITEMASK             = 0xB98,
        GL_MATRIX_MODE                   = 0xBA0,
        GL_NORMALIZE                     = 0xBA1,
        GL_VIEWPORT                      = 0xBA2,
        GL_MODELVIEW_STACK_DEPTH         = 0xBA3,
        GL_PROJECTION_STACK_DEPTH        = 0xBA4,
        GL_TEXTURE_STACK_DEPTH           = 0xBA5,
        GL_MODELVIEW_MATRIX              = 0xBA6,
        GL_PROJECTION_MATRIX             = 0xBA7,
        GL_TEXTURE_MATRIX                = 0xBA8,
        GL_ATTRIB_STACK_DEPTH            = 0xBB0,
        GL_CLIENT_ATTRIB_STACK_DEPTH     = 0xBB1,
        GL_ALPHA_TEST                    = 0xBC0,
        GL_ALPHA_TEST_FUNC               = 0xBC1,
        GL_ALPHA_TEST_REF                = 0xBC2,
        GL_DITHER                        = 0xBD0,
        GL_BLEND_DST                     = 0xBE0,
        GL_BLEND_SRC                     = 0xBE1,
        GL_BLEND                         = 0xBE2,
        GL_LOGIC_OP_MODE                 = 0xBF0,
        GL_INDEX_LOGIC_OP                = 0xBF1,
        GL_LOGIC_OP                      = 0xBF1,
        GL_COLOR_LOGIC_OP                = 0xBF2,
        GL_AUX_BUFFERS                   = 0xC00,
        GL_DRAW_BUFFER                   = 0xC01,
        GL_READ_BUFFER                   = 0xC02,
        GL_SCISSOR_BOX                   = 0xC10,
        GL_SCISSOR_TEST                  = 0xC11,
        GL_INDEX_CLEAR_VALUE             = 0xC20,
        GL_INDEX_WRITEMASK               = 0xC21,
        GL_COLOR_CLEAR_VALUE             = 0xC22,
        GL_COLOR_WRITEMASK               = 0xC23,
        GL_INDEX_MODE                    = 0xC30,
        GL_RGBA_MODE                     = 0xC31,
        GL_DOUBLEBUFFER                  = 0xC32,
        GL_STEREO                        = 0xC33,
        GL_RENDER_MODE                   = 0xC40,
        GL_PERSPECTIVE_CORRECTION_HINT   = 0xC50,
        GL_POINT_SMOOTH_HINT             = 0xC51,
        GL_LINE_SMOOTH_HINT              = 0xC52,
        GL_POLYGON_SMOOTH_HINT           = 0xC53,
        GL_FOG_HINT                      = 0xC54,
        GL_TEXTURE_GEN_S                 = 0xC60,
        GL_TEXTURE_GEN_T                 = 0xC61,
        GL_TEXTURE_GEN_R                 = 0xC62,
        GL_TEXTURE_GEN_Q                 = 0xC63,
        GL_PIXEL_MAP_I_TO_I              = 0xC70,
        GL_PIXEL_MAP_S_TO_S              = 0xC71,
        GL_PIXEL_MAP_I_TO_R              = 0xC72,
        GL_PIXEL_MAP_I_TO_G              = 0xC73,
        GL_PIXEL_MAP_I_TO_B              = 0xC74,
        GL_PIXEL_MAP_I_TO_A              = 0xC75,
        GL_PIXEL_MAP_R_TO_R              = 0xC76,
        GL_PIXEL_MAP_G_TO_G              = 0xC77,
        GL_PIXEL_MAP_B_TO_B              = 0xC78,
        GL_PIXEL_MAP_A_TO_A              = 0xC79,
        GL_PIXEL_MAP_I_TO_I_SIZE         = 0xCB0,
        GL_PIXEL_MAP_S_TO_S_SIZE         = 0xCB1,
        GL_PIXEL_MAP_I_TO_R_SIZE         = 0xCB2,
        GL_PIXEL_MAP_I_TO_G_SIZE         = 0xCB3,
        GL_PIXEL_MAP_I_TO_B_SIZE         = 0xCB4,
        GL_PIXEL_MAP_I_TO_A_SIZE         = 0xCB5,
        GL_PIXEL_MAP_R_TO_R_SIZE         = 0xCB6,
        GL_PIXEL_MAP_G_TO_G_SIZE         = 0xCB7,
        GL_PIXEL_MAP_B_TO_B_SIZE         = 0xCB8,
        GL_PIXEL_MAP_A_TO_A_SIZE         = 0xCB9,
        GL_UNPACK_SWAP_BYTES             = 0xCF0,
        GL_UNPACK_LSB_FIRST              = 0xCF1,
        GL_UNPACK_ROW_LENGTH             = 0xCF2,
        GL_UNPACK_SKIP_ROWS              = 0xCF3,
        GL_UNPACK_SKIP_PIXELS            = 0xCF4,
        GL_UNPACK_ALIGNMENT              = 0xCF5,
        GL_PACK_SWAP_BYTES               = 0xD00,
        GL_PACK_LSB_FIRST                = 0xD01,
        GL_PACK_ROW_LENGTH               = 0xD02,
        GL_PACK_SKIP_ROWS                = 0xD03,
        GL_PACK_SKIP_PIXELS              = 0xD04,
        GL_PACK_ALIGNMENT                = 0xD05,
        GL_MAP_COLOR                     = 0xD10,
        GL_MAP_STENCIL                   = 0xD11,
        GL_INDEX_SHIFT                   = 0xD12,
        GL_INDEX_OFFSET                  = 0xD13,
        GL_RED_SCALE                     = 0xD14,
        GL_RED_BIAS                      = 0xD15,
        GL_ZOOM_X                        = 0xD16,
        GL_ZOOM_Y                        = 0xD17,
        GL_GREEN_SCALE                   = 0xD18,
        GL_GREEN_BIAS                    = 0xD19,
        GL_BLUE_SCALE                    = 0xD1A,
        GL_BLUE_BIAS                     = 0xD1B,
        GL_ALPHA_SCALE                   = 0xD1C,
        GL_ALPHA_BIAS                    = 0xD1D,
        GL_DEPTH_SCALE                   = 0xD1E,
        GL_DEPTH_BIAS                    = 0xD1F,
        GL_MAX_EVAL_ORDER                = 0xD30,
        GL_MAX_LIGHTS                    = 0xD31,
        GL_MAX_CLIP_PLANES               = 0xD32,
        GL_MAX_TEXTURE_SIZE              = 0xD33,
        GL_MAX_PIXEL_MAP_TABLE           = 0xD34,
        GL_MAX_ATTRIB_STACK_DEPTH        = 0xD35,
        GL_MAX_MODELVIEW_STACK_DEPTH     = 0xD36,
        GL_MAX_NAME_STACK_DEPTH          = 0xD37,
        GL_MAX_PROJECTION_STACK_DEPTH    = 0xD38,
        GL_MAX_TEXTURE_STACK_DEPTH       = 0xD39,
        GL_MAX_VIEWPORT_DIMS             = 0xD3A,
        GL_MAX_CLIENT_ATTRIB_STACK_DEPTH = 0xD3B,
        GL_SUBPIXEL_BITS                 = 0xD50,
        GL_INDEX_BITS                    = 0xD51,
        GL_RED_BITS                      = 0xD52,
        GL_GREEN_BITS                    = 0xD53,
        GL_BLUE_BITS                     = 0xD54,
        GL_ALPHA_BITS                    = 0xD55,
        GL_DEPTH_BITS                    = 0xD56,
        GL_STENCIL_BITS                  = 0xD57,
        GL_ACCUM_RED_BITS                = 0xD58,
        GL_ACCUM_GREEN_BITS              = 0xD59,
        GL_ACCUM_BLUE_BITS               = 0xD5A,
        GL_ACCUM_ALPHA_BITS              = 0xD5B,
        GL_NAME_STACK_DEPTH              = 0xD70,
        GL_AUTO_NORMAL                   = 0xD80,
        GL_MAP1_COLOR_4                  = 0xD90,
        GL_MAP1_INDEX                    = 0xD91,
        GL_MAP1_NORMAL                   = 0xD92,
        GL_MAP1_TEXTURE_COORD_1          = 0xD93,
        GL_MAP1_TEXTURE_COORD_2          = 0xD94,
        GL_MAP1_TEXTURE_COORD_3          = 0xD95,
        GL_MAP1_TEXTURE_COORD_4          = 0xD96,
        GL_MAP1_VERTEX_3                 = 0xD97,
        GL_MAP1_VERTEX_4                 = 0xD98,
        GL_MAP2_COLOR_4                  = 0xDB0,
        GL_MAP2_INDEX                    = 0xDB1,
        GL_MAP2_NORMAL                   = 0xDB2,
        GL_MAP2_TEXTURE_COORD_1          = 0xDB3,
        GL_MAP2_TEXTURE_COORD_2          = 0xDB4,
        GL_MAP2_TEXTURE_COORD_3          = 0xDB5,
        GL_MAP2_TEXTURE_COORD_4          = 0xDB6,
        GL_MAP2_VERTEX_3                 = 0xDB7,
        GL_MAP2_VERTEX_4                 = 0xDB8,
        GL_MAP1_GRID_DOMAIN              = 0xDD0,
        GL_MAP1_GRID_SEGMENTS            = 0xDD1,
        GL_MAP2_GRID_DOMAIN              = 0xDD2,
        GL_MAP2_GRID_SEGMENTS            = 0xDD3,
        GL_TEXTURE_1D                    = 0xDE0,
        GL_TEXTURE_2D                    = 0xDE1,
        GL_FEEDBACK_BUFFER_POINTER       = 0xDF0,
        GL_FEEDBACK_BUFFER_SIZE          = 0xDF1,
        GL_FEEDBACK_BUFFER_TYPE          = 0xDF2,
        GL_SELECTION_BUFFER_POINTER      = 0xDF3,
        GL_SELECTION_BUFFER_SIZE         = 0xDF4;

    int
        GL_TEXTURE_WIDTH           = 0x1000,
        GL_TEXTURE_HEIGHT          = 0x1001,
        GL_TEXTURE_INTERNAL_FORMAT = 0x1003,
        GL_TEXTURE_COMPONENTS      = 0x1003,
        GL_TEXTURE_BORDER_COLOR    = 0x1004,
        GL_TEXTURE_BORDER          = 0x1005;

    int
        GL_DONT_CARE = 0x1100,
        GL_FASTEST   = 0x1101,
        GL_NICEST    = 0x1102;

    int
        GL_LIGHT0 = 0x4000,
        GL_LIGHT1 = 0x4001,
        GL_LIGHT2 = 0x4002,
        GL_LIGHT3 = 0x4003,
        GL_LIGHT4 = 0x4004,
        GL_LIGHT5 = 0x4005,
        GL_LIGHT6 = 0x4006,
        GL_LIGHT7 = 0x4007;

    int
        GL_AMBIENT               = 0x1200,
        GL_DIFFUSE               = 0x1201,
        GL_SPECULAR              = 0x1202,
        GL_POSITION              = 0x1203,
        GL_SPOT_DIRECTION        = 0x1204,
        GL_SPOT_EXPONENT         = 0x1205,
        GL_SPOT_CUTOFF           = 0x1206,
        GL_CONSTANT_ATTENUATION  = 0x1207,
        GL_LINEAR_ATTENUATION    = 0x1208,
        GL_QUADRATIC_ATTENUATION = 0x1209;

    int
        GL_COMPILE             = 0x1300,
        GL_COMPILE_AND_EXECUTE = 0x1301;

    int
        GL_CLEAR         = 0x1500,
        GL_AND           = 0x1501,
        GL_AND_REVERSE   = 0x1502,
        GL_COPY          = 0x1503,
        GL_AND_INVERTED  = 0x1504,
        GL_NOOP          = 0x1505,
        GL_XOR           = 0x1506,
        GL_OR            = 0x1507,
        GL_NOR           = 0x1508,
        GL_EQUIV         = 0x1509,
        GL_INVERT        = 0x150A,
        GL_OR_REVERSE    = 0x150B,
        GL_COPY_INVERTED = 0x150C,
        GL_OR_INVERTED   = 0x150D,
        GL_NAND          = 0x150E,
        GL_SET           = 0x150F;

    int
        GL_EMISSION            = 0x1600,
        GL_SHININESS           = 0x1601,
        GL_AMBIENT_AND_DIFFUSE = 0x1602,
        GL_COLOR_INDEXES       = 0x1603;

    int
        GL_MODELVIEW  = 0x1700,
        GL_PROJECTION = 0x1701,
        GL_TEXTURE    = 0x1702;

    int
        GL_COLOR   = 0x1800,
        GL_DEPTH   = 0x1801,
        GL_STENCIL = 0x1802;

    int
        GL_COLOR_INDEX     = 0x1900,
        GL_STENCIL_INDEX   = 0x1901,
        GL_DEPTH_COMPONENT = 0x1902,
        GL_RED             = 0x1903,
        GL_GREEN           = 0x1904,
        GL_BLUE            = 0x1905,
        GL_ALPHA           = 0x1906,
        GL_RGB             = 0x1907,
        GL_RGBA            = 0x1908,
        GL_LUMINANCE       = 0x1909,
        GL_LUMINANCE_ALPHA = 0x190A;

    int GL_BITMAP = 0x1A00;

    int
        GL_POINT = 0x1B00,
        GL_LINE  = 0x1B01,
        GL_FILL  = 0x1B02;

    int
        GL_RENDER   = 0x1C00,
        GL_FEEDBACK = 0x1C01,
        GL_SELECT   = 0x1C02;

    int
        GL_FLAT   = 0x1D00,
        GL_SMOOTH = 0x1D01;

    int
        GL_KEEP    = 0x1E00,
        GL_REPLACE = 0x1E01,
        GL_INCR    = 0x1E02,
        GL_DECR    = 0x1E03;

    int
        GL_VENDOR     = 0x1F00,
        GL_RENDERER   = 0x1F01,
        GL_VERSION    = 0x1F02,
        GL_EXTENSIONS = 0x1F03;

    int
        GL_S = 0x2000,
        GL_T = 0x2001,
        GL_R = 0x2002,
        GL_Q = 0x2003;

    int
        GL_MODULATE = 0x2100,
        GL_DECAL    = 0x2101;

    int
        GL_TEXTURE_ENV_MODE  = 0x2200,
        GL_TEXTURE_ENV_COLOR = 0x2201;

    int GL_TEXTURE_ENV = 0x2300;

    int
        GL_EYE_LINEAR    = 0x2400,
        GL_OBJECT_LINEAR = 0x2401,
        GL_SPHERE_MAP    = 0x2402;

    int
        GL_TEXTURE_GEN_MODE = 0x2500,
        GL_OBJECT_PLANE     = 0x2501,
        GL_EYE_PLANE        = 0x2502;

    int
        GL_NEAREST = 0x2600,
        GL_LINEAR  = 0x2601;

    int
        GL_NEAREST_MIPMAP_NEAREST = 0x2700,
        GL_LINEAR_MIPMAP_NEAREST  = 0x2701,
        GL_NEAREST_MIPMAP_LINEAR  = 0x2702,
        GL_LINEAR_MIPMAP_LINEAR   = 0x2703;

    int
        GL_TEXTURE_MAG_FILTER = 0x2800,
        GL_TEXTURE_MIN_FILTER = 0x2801,
        GL_TEXTURE_WRAP_S     = 0x2802,
        GL_TEXTURE_WRAP_T     = 0x2803;

    int
        GL_CLAMP  = 0x2900,
        GL_REPEAT = 0x2901;

    int
        GL_CLIENT_PIXEL_STORE_BIT  = 0x1,
        GL_CLIENT_VERTEX_ARRAY_BIT = 0x2,
        GL_CLIENT_ALL_ATTRIB_BITS  = 0xFFFFFFFF;

    int
        GL_POLYGON_OFFSET_FACTOR = 0x8038,
        GL_POLYGON_OFFSET_UNITS  = 0x2A00,
        GL_POLYGON_OFFSET_POINT  = 0x2A01,
        GL_POLYGON_OFFSET_LINE   = 0x2A02,
        GL_POLYGON_OFFSET_FILL   = 0x8037;

    int
        GL_ALPHA4                 = 0x803B,
        GL_ALPHA8                 = 0x803C,
        GL_ALPHA12                = 0x803D,
        GL_ALPHA16                = 0x803E,
        GL_LUMINANCE4             = 0x803F,
        GL_LUMINANCE8             = 0x8040,
        GL_LUMINANCE12            = 0x8041,
        GL_LUMINANCE16            = 0x8042,
        GL_LUMINANCE4_ALPHA4      = 0x8043,
        GL_LUMINANCE6_ALPHA2      = 0x8044,
        GL_LUMINANCE8_ALPHA8      = 0x8045,
        GL_LUMINANCE12_ALPHA4     = 0x8046,
        GL_LUMINANCE12_ALPHA12    = 0x8047,
        GL_LUMINANCE16_ALPHA16    = 0x8048,
        GL_INTENSITY              = 0x8049,
        GL_INTENSITY4             = 0x804A,
        GL_INTENSITY8             = 0x804B,
        GL_INTENSITY12            = 0x804C,
        GL_INTENSITY16            = 0x804D,
        GL_R3_G3_B2               = 0x2A10,
        GL_RGB4                   = 0x804F,
        GL_RGB5                   = 0x8050,
        GL_RGB8                   = 0x8051,
        GL_RGB10                  = 0x8052,
        GL_RGB12                  = 0x8053,
        GL_RGB16                  = 0x8054,
        GL_RGBA2                  = 0x8055,
        GL_RGBA4                  = 0x8056,
        GL_RGB5_A1                = 0x8057,
        GL_RGBA8                  = 0x8058,
        GL_RGB10_A2               = 0x8059,
        GL_RGBA12                 = 0x805A,
        GL_RGBA16                 = 0x805B,
        GL_TEXTURE_RED_SIZE       = 0x805C,
        GL_TEXTURE_GREEN_SIZE     = 0x805D,
        GL_TEXTURE_BLUE_SIZE      = 0x805E,
        GL_TEXTURE_ALPHA_SIZE     = 0x805F,
        GL_TEXTURE_LUMINANCE_SIZE = 0x8060,
        GL_TEXTURE_INTENSITY_SIZE = 0x8061,
        GL_PROXY_TEXTURE_1D       = 0x8063,
        GL_PROXY_TEXTURE_2D       = 0x8064;

    int
        GL_TEXTURE_PRIORITY   = 0x8066,
        GL_TEXTURE_RESIDENT   = 0x8067,
        GL_TEXTURE_BINDING_1D = 0x8068,
        GL_TEXTURE_BINDING_2D = 0x8069;

    int
        GL_VERTEX_ARRAY                = 0x8074,
        GL_NORMAL_ARRAY                = 0x8075,
        GL_COLOR_ARRAY                 = 0x8076,
        GL_INDEX_ARRAY                 = 0x8077,
        GL_TEXTURE_COORD_ARRAY         = 0x8078,
        GL_EDGE_FLAG_ARRAY             = 0x8079,
        GL_VERTEX_ARRAY_SIZE           = 0x807A,
        GL_VERTEX_ARRAY_TYPE           = 0x807B,
        GL_VERTEX_ARRAY_STRIDE         = 0x807C,
        GL_NORMAL_ARRAY_TYPE           = 0x807E,
        GL_NORMAL_ARRAY_STRIDE         = 0x807F,
        GL_COLOR_ARRAY_SIZE            = 0x8081,
        GL_COLOR_ARRAY_TYPE            = 0x8082,
        GL_COLOR_ARRAY_STRIDE          = 0x8083,
        GL_INDEX_ARRAY_TYPE            = 0x8085,
        GL_INDEX_ARRAY_STRIDE          = 0x8086,
        GL_TEXTURE_COORD_ARRAY_SIZE    = 0x8088,
        GL_TEXTURE_COORD_ARRAY_TYPE    = 0x8089,
        GL_TEXTURE_COORD_ARRAY_STRIDE  = 0x808A,
        GL_EDGE_FLAG_ARRAY_STRIDE      = 0x808C,
        GL_VERTEX_ARRAY_POINTER        = 0x808E,
        GL_NORMAL_ARRAY_POINTER        = 0x808F,
        GL_COLOR_ARRAY_POINTER         = 0x8090,
        GL_INDEX_ARRAY_POINTER         = 0x8091,
        GL_TEXTURE_COORD_ARRAY_POINTER = 0x8092,
        GL_EDGE_FLAG_ARRAY_POINTER     = 0x8093,
        GL_V2F                         = 0x2A20,
        GL_V3F                         = 0x2A21,
        GL_C4UB_V2F                    = 0x2A22,
        GL_C4UB_V3F                    = 0x2A23,
        GL_C3F_V3F                     = 0x2A24,
        GL_N3F_V3F                     = 0x2A25,
        GL_C4F_N3F_V3F                 = 0x2A26,
        GL_T2F_V3F                     = 0x2A27,
        GL_T4F_V4F                     = 0x2A28,
        GL_T2F_C4UB_V3F                = 0x2A29,
        GL_T2F_C3F_V3F                 = 0x2A2A,
        GL_T2F_N3F_V3F                 = 0x2A2B,
        GL_T2F_C4F_N3F_V3F             = 0x2A2C,
        GL_T4F_C4F_N3F_V4F             = 0x2A2D;

    // GL 1.2 Constants

    int
        GL_ALIASED_POINT_SIZE_RANGE      = 0x846D,
        GL_ALIASED_LINE_WIDTH_RANGE      = 0x846E,
        GL_SMOOTH_POINT_SIZE_RANGE       = 0xB12,
        GL_SMOOTH_POINT_SIZE_GRANULARITY = 0xB13,
        GL_SMOOTH_LINE_WIDTH_RANGE       = 0xB22,
        GL_SMOOTH_LINE_WIDTH_GRANULARITY = 0xB23;

    int GL_TEXTURE_BINDING_3D = 0x806A;

    int
        GL_PACK_SKIP_IMAGES    = 0x806B,
        GL_PACK_IMAGE_HEIGHT   = 0x806C,
        GL_UNPACK_SKIP_IMAGES  = 0x806D,
        GL_UNPACK_IMAGE_HEIGHT = 0x806E;

    int GL_TEXTURE_3D = 0x806F;

    int GL_PROXY_TEXTURE_3D = 0x8070;

    int GL_TEXTURE_DEPTH = 0x8071;

    int GL_TEXTURE_WRAP_R = 0x8072;

    int GL_MAX_3D_TEXTURE_SIZE = 0x8073;

    int
        GL_BGR  = 0x80E0,
        GL_BGRA = 0x80E1;

    int
        GL_UNSIGNED_BYTE_3_3_2         = 0x8032,
        GL_UNSIGNED_BYTE_2_3_3_REV     = 0x8362,
        GL_UNSIGNED_SHORT_5_6_5        = 0x8363,
        GL_UNSIGNED_SHORT_5_6_5_REV    = 0x8364,
        GL_UNSIGNED_SHORT_4_4_4_4      = 0x8033,
        GL_UNSIGNED_SHORT_4_4_4_4_REV  = 0x8365,
        GL_UNSIGNED_SHORT_5_5_5_1      = 0x8034,
        GL_UNSIGNED_SHORT_1_5_5_5_REV  = 0x8366,
        GL_UNSIGNED_INT_8_8_8_8        = 0x8035,
        GL_UNSIGNED_INT_8_8_8_8_REV    = 0x8367,
        GL_UNSIGNED_INT_10_10_10_2     = 0x8036,
        GL_UNSIGNED_INT_2_10_10_10_REV = 0x8368;

    int GL_RESCALE_NORMAL = 0x803A;

    int GL_LIGHT_MODEL_COLOR_CONTROL = 0x81F8;

    int
        GL_SINGLE_COLOR            = 0x81F9,
        GL_SEPARATE_SPECULAR_COLOR = 0x81FA;

    int GL_CLAMP_TO_EDGE = 0x812F;

    int
        GL_TEXTURE_MIN_LOD    = 0x813A,
        GL_TEXTURE_MAX_LOD    = 0x813B,
        GL_TEXTURE_BASE_LEVEL = 0x813C,
        GL_TEXTURE_MAX_LEVEL  = 0x813D;

    int
        GL_MAX_ELEMENTS_VERTICES = 0x80E8,
        GL_MAX_ELEMENTS_INDICES  = 0x80E9;

    // GL 1.3 Constants

    int
        GL_COMPRESSED_ALPHA           = 0x84E9,
        GL_COMPRESSED_LUMINANCE       = 0x84EA,
        GL_COMPRESSED_LUMINANCE_ALPHA = 0x84EB,
        GL_COMPRESSED_INTENSITY       = 0x84EC,
        GL_COMPRESSED_RGB             = 0x84ED,
        GL_COMPRESSED_RGBA            = 0x84EE;

    int GL_TEXTURE_COMPRESSION_HINT = 0x84EF;

    int
        GL_TEXTURE_COMPRESSED_IMAGE_SIZE = 0x86A0,
        GL_TEXTURE_COMPRESSED            = 0x86A1;

    int
        GL_NUM_COMPRESSED_TEXTURE_FORMATS = 0x86A2,
        GL_COMPRESSED_TEXTURE_FORMATS     = 0x86A3;

    int
        GL_NORMAL_MAP     = 0x8511,
        GL_REFLECTION_MAP = 0x8512;

    int GL_TEXTURE_CUBE_MAP = 0x8513;

    int GL_TEXTURE_BINDING_CUBE_MAP = 0x8514;

    int
        GL_TEXTURE_CUBE_MAP_POSITIVE_X = 0x8515,
        GL_TEXTURE_CUBE_MAP_NEGATIVE_X = 0x8516,
        GL_TEXTURE_CUBE_MAP_POSITIVE_Y = 0x8517,
        GL_TEXTURE_CUBE_MAP_NEGATIVE_Y = 0x8518,
        GL_TEXTURE_CUBE_MAP_POSITIVE_Z = 0x8519,
        GL_TEXTURE_CUBE_MAP_NEGATIVE_Z = 0x851A;

    int GL_PROXY_TEXTURE_CUBE_MAP = 0x851B;

    int GL_MAX_CUBE_MAP_TEXTURE_SIZE = 0x851C;

    int
        GL_MULTISAMPLE              = 0x809D,
        GL_SAMPLE_ALPHA_TO_COVERAGE = 0x809E,
        GL_SAMPLE_ALPHA_TO_ONE      = 0x809F,
        GL_SAMPLE_COVERAGE          = 0x80A0;

    int GL_MULTISAMPLE_BIT = 0x20000000;

    int
        GL_SAMPLE_BUFFERS         = 0x80A8,
        GL_SAMPLES                = 0x80A9,
        GL_SAMPLE_COVERAGE_VALUE  = 0x80AA,
        GL_SAMPLE_COVERAGE_INVERT = 0x80AB;

    int
        GL_TEXTURE0  = 0x84C0,
        GL_TEXTURE1  = 0x84C1,
        GL_TEXTURE2  = 0x84C2,
        GL_TEXTURE3  = 0x84C3,
        GL_TEXTURE4  = 0x84C4,
        GL_TEXTURE5  = 0x84C5,
        GL_TEXTURE6  = 0x84C6,
        GL_TEXTURE7  = 0x84C7,
        GL_TEXTURE8  = 0x84C8,
        GL_TEXTURE9  = 0x84C9,
        GL_TEXTURE10 = 0x84CA,
        GL_TEXTURE11 = 0x84CB,
        GL_TEXTURE12 = 0x84CC,
        GL_TEXTURE13 = 0x84CD,
        GL_TEXTURE14 = 0x84CE,
        GL_TEXTURE15 = 0x84CF,
        GL_TEXTURE16 = 0x84D0,
        GL_TEXTURE17 = 0x84D1,
        GL_TEXTURE18 = 0x84D2,
        GL_TEXTURE19 = 0x84D3,
        GL_TEXTURE20 = 0x84D4,
        GL_TEXTURE21 = 0x84D5,
        GL_TEXTURE22 = 0x84D6,
        GL_TEXTURE23 = 0x84D7,
        GL_TEXTURE24 = 0x84D8,
        GL_TEXTURE25 = 0x84D9,
        GL_TEXTURE26 = 0x84DA,
        GL_TEXTURE27 = 0x84DB,
        GL_TEXTURE28 = 0x84DC,
        GL_TEXTURE29 = 0x84DD,
        GL_TEXTURE30 = 0x84DE,
        GL_TEXTURE31 = 0x84DF;

    int
        GL_ACTIVE_TEXTURE        = 0x84E0,
        GL_CLIENT_ACTIVE_TEXTURE = 0x84E1,
        GL_MAX_TEXTURE_UNITS     = 0x84E2;

    int GL_COMBINE = 0x8570;

    int
        GL_COMBINE_RGB    = 0x8571,
        GL_COMBINE_ALPHA  = 0x8572,
        GL_SOURCE0_RGB    = 0x8580,
        GL_SOURCE1_RGB    = 0x8581,
        GL_SOURCE2_RGB    = 0x8582,
        GL_SOURCE0_ALPHA  = 0x8588,
        GL_SOURCE1_ALPHA  = 0x8589,
        GL_SOURCE2_ALPHA  = 0x858A,
        GL_OPERAND0_RGB   = 0x8590,
        GL_OPERAND1_RGB   = 0x8591,
        GL_OPERAND2_RGB   = 0x8592,
        GL_OPERAND0_ALPHA = 0x8598,
        GL_OPERAND1_ALPHA = 0x8599,
        GL_OPERAND2_ALPHA = 0x859A,
        GL_RGB_SCALE      = 0x8573;

    int
        GL_ADD_SIGNED  = 0x8574,
        GL_INTERPOLATE = 0x8575,
        GL_SUBTRACT    = 0x84E7;

    int
        GL_CONSTANT      = 0x8576,
        GL_PRIMARY_COLOR = 0x8577,
        GL_PREVIOUS      = 0x8578;

    int
        GL_DOT3_RGB  = 0x86AE,
        GL_DOT3_RGBA = 0x86AF;

    int GL_CLAMP_TO_BORDER = 0x812D;

    int
        GL_TRANSPOSE_MODELVIEW_MATRIX  = 0x84E3,
        GL_TRANSPOSE_PROJECTION_MATRIX = 0x84E4,
        GL_TRANSPOSE_TEXTURE_MATRIX    = 0x84E5,
        GL_TRANSPOSE_COLOR_MATRIX      = 0x84E6;

    // GL 1.4 Constants

    int GL_GENERATE_MIPMAP = 0x8191;

    int GL_GENERATE_MIPMAP_HINT = 0x8192;

    int
        GL_CONSTANT_COLOR           = 0x8001,
        GL_ONE_MINUS_CONSTANT_COLOR = 0x8002,
        GL_CONSTANT_ALPHA           = 0x8003,
        GL_ONE_MINUS_CONSTANT_ALPHA = 0x8004;

    int GL_BLEND_COLOR = 0x8005;

    int
        GL_FUNC_ADD = 0x8006,
        GL_MIN      = 0x8007,
        GL_MAX      = 0x8008;

    int GL_BLEND_EQUATION = 0x8009;

    int
        GL_FUNC_SUBTRACT         = 0x800A,
        GL_FUNC_REVERSE_SUBTRACT = 0x800B;

    int
        GL_DEPTH_COMPONENT16 = 0x81A5,
        GL_DEPTH_COMPONENT24 = 0x81A6,
        GL_DEPTH_COMPONENT32 = 0x81A7;

    int GL_TEXTURE_DEPTH_SIZE = 0x884A;

    int GL_DEPTH_TEXTURE_MODE = 0x884B;

    int
        GL_TEXTURE_COMPARE_MODE = 0x884C,
        GL_TEXTURE_COMPARE_FUNC = 0x884D;

    int GL_COMPARE_R_TO_TEXTURE = 0x884E;

    int GL_FOG_COORDINATE_SOURCE = 0x8450;

    int
        GL_FOG_COORDINATE = 0x8451,
        GL_FRAGMENT_DEPTH = 0x8452;

    int
        GL_CURRENT_FOG_COORDINATE      = 0x8453,
        GL_FOG_COORDINATE_ARRAY_TYPE   = 0x8454,
        GL_FOG_COORDINATE_ARRAY_STRIDE = 0x8455;

    int GL_FOG_COORDINATE_ARRAY_POINTER = 0x8456;

    int GL_FOG_COORDINATE_ARRAY = 0x8457;

    int
        GL_POINT_SIZE_MIN             = 0x8126,
        GL_POINT_SIZE_MAX             = 0x8127,
        GL_POINT_FADE_THRESHOLD_SIZE  = 0x8128,
        GL_POINT_DISTANCE_ATTENUATION = 0x8129;

    int GL_COLOR_SUM = 0x8458;

    int
        GL_CURRENT_SECONDARY_COLOR      = 0x8459,
        GL_SECONDARY_COLOR_ARRAY_SIZE   = 0x845A,
        GL_SECONDARY_COLOR_ARRAY_TYPE   = 0x845B,
        GL_SECONDARY_COLOR_ARRAY_STRIDE = 0x845C;

    int GL_SECONDARY_COLOR_ARRAY_POINTER = 0x845D;

    int GL_SECONDARY_COLOR_ARRAY = 0x845E;

    int
        GL_BLEND_DST_RGB   = 0x80C8,
        GL_BLEND_SRC_RGB   = 0x80C9,
        GL_BLEND_DST_ALPHA = 0x80CA,
        GL_BLEND_SRC_ALPHA = 0x80CB;

    int
        GL_INCR_WRAP = 0x8507,
        GL_DECR_WRAP = 0x8508;

    int GL_TEXTURE_FILTER_CONTROL = 0x8500;

    int GL_TEXTURE_LOD_BIAS = 0x8501;

    int GL_MAX_TEXTURE_LOD_BIAS = 0x84FD;

    int GL_MIRRORED_REPEAT = 0x8370;

    // GL 1.5 Constants

    int
        GL_FOG_COORD_SRC                  = 0x8450,
        GL_FOG_COORD                      = 0x8451,
        GL_CURRENT_FOG_COORD              = 0x8453,
        GL_FOG_COORD_ARRAY_TYPE           = 0x8454,
        GL_FOG_COORD_ARRAY_STRIDE         = 0x8455,
        GL_FOG_COORD_ARRAY_POINTER        = 0x8456,
        GL_FOG_COORD_ARRAY                = 0x8457,
        GL_FOG_COORD_ARRAY_BUFFER_BINDING = 0x889D,
        GL_SRC0_RGB                       = 0x8580,
        GL_SRC1_RGB                       = 0x8581,
        GL_SRC2_RGB                       = 0x8582,
        GL_SRC0_ALPHA                     = 0x8588,
        GL_SRC1_ALPHA                     = 0x8589,
        GL_SRC2_ALPHA                     = 0x858A;

    int
        GL_ARRAY_BUFFER         = 0x8892,
        GL_ELEMENT_ARRAY_BUFFER = 0x8893;

    int
        GL_ARRAY_BUFFER_BINDING                 = 0x8894,
        GL_ELEMENT_ARRAY_BUFFER_BINDING         = 0x8895,
        GL_VERTEX_ARRAY_BUFFER_BINDING          = 0x8896,
        GL_NORMAL_ARRAY_BUFFER_BINDING          = 0x8897,
        GL_COLOR_ARRAY_BUFFER_BINDING           = 0x8898,
        GL_INDEX_ARRAY_BUFFER_BINDING           = 0x8899,
        GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING   = 0x889A,
        GL_EDGE_FLAG_ARRAY_BUFFER_BINDING       = 0x889B,
        GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 0x889C,
        GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING  = 0x889D,
        GL_WEIGHT_ARRAY_BUFFER_BINDING          = 0x889E;

    int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 0x889F;

    int
        GL_STREAM_DRAW  = 0x88E0,
        GL_STREAM_READ  = 0x88E1,
        GL_STREAM_COPY  = 0x88E2,
        GL_STATIC_DRAW  = 0x88E4,
        GL_STATIC_READ  = 0x88E5,
        GL_STATIC_COPY  = 0x88E6,
        GL_DYNAMIC_DRAW = 0x88E8,
        GL_DYNAMIC_READ = 0x88E9,
        GL_DYNAMIC_COPY = 0x88EA;

    int
        GL_READ_ONLY  = 0x88B8,
        GL_WRITE_ONLY = 0x88B9,
        GL_READ_WRITE = 0x88BA;

    int
        GL_BUFFER_SIZE   = 0x8764,
        GL_BUFFER_USAGE  = 0x8765,
        GL_BUFFER_ACCESS = 0x88BB,
        GL_BUFFER_MAPPED = 0x88BC;

    int GL_BUFFER_MAP_POINTER = 0x88BD;

    int GL_SAMPLES_PASSED = 0x8914;

    int
        GL_QUERY_COUNTER_BITS = 0x8864,
        GL_CURRENT_QUERY      = 0x8865;

    int
        GL_QUERY_RESULT           = 0x8866,
        GL_QUERY_RESULT_AVAILABLE = 0x8867;

    // GL 2.0 Constants

    int GL_SHADING_LANGUAGE_VERSION = 0x8B8C;

    int GL_CURRENT_PROGRAM = 0x8B8D;

    int
        GL_SHADER_TYPE                 = 0x8B4F,
        GL_DELETE_STATUS               = 0x8B80,
        GL_COMPILE_STATUS              = 0x8B81,
        GL_LINK_STATUS                 = 0x8B82,
        GL_VALIDATE_STATUS             = 0x8B83,
        GL_INFO_LOG_LENGTH             = 0x8B84,
        GL_ATTACHED_SHADERS            = 0x8B85,
        GL_ACTIVE_UNIFORMS             = 0x8B86,
        GL_ACTIVE_UNIFORM_MAX_LENGTH   = 0x8B87,
        GL_ACTIVE_ATTRIBUTES           = 0x8B89,
        GL_ACTIVE_ATTRIBUTE_MAX_LENGTH = 0x8B8A,
        GL_SHADER_SOURCE_LENGTH        = 0x8B88;

    int
        GL_FLOAT_VEC2        = 0x8B50,
        GL_FLOAT_VEC3        = 0x8B51,
        GL_FLOAT_VEC4        = 0x8B52,
        GL_INT_VEC2          = 0x8B53,
        GL_INT_VEC3          = 0x8B54,
        GL_INT_VEC4          = 0x8B55,
        GL_BOOL              = 0x8B56,
        GL_BOOL_VEC2         = 0x8B57,
        GL_BOOL_VEC3         = 0x8B58,
        GL_BOOL_VEC4         = 0x8B59,
        GL_FLOAT_MAT2        = 0x8B5A,
        GL_FLOAT_MAT3        = 0x8B5B,
        GL_FLOAT_MAT4        = 0x8B5C,
        GL_SAMPLER_1D        = 0x8B5D,
        GL_SAMPLER_2D        = 0x8B5E,
        GL_SAMPLER_3D        = 0x8B5F,
        GL_SAMPLER_CUBE      = 0x8B60,
        GL_SAMPLER_1D_SHADOW = 0x8B61,
        GL_SAMPLER_2D_SHADOW = 0x8B62;

    int GL_VERTEX_SHADER = 0x8B31;

    int
        GL_MAX_VERTEX_UNIFORM_COMPONENTS    = 0x8B4A,
        GL_MAX_VARYING_FLOATS               = 0x8B4B,
        GL_MAX_VERTEX_ATTRIBS               = 0x8869,
        GL_MAX_TEXTURE_IMAGE_UNITS          = 0x8872,
        GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS   = 0x8B4C,
        GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS = 0x8B4D,
        GL_MAX_TEXTURE_COORDS               = 0x8871;

    int
        GL_VERTEX_PROGRAM_POINT_SIZE = 0x8642,
        GL_VERTEX_PROGRAM_TWO_SIDE   = 0x8643;

    int
        GL_VERTEX_ATTRIB_ARRAY_ENABLED    = 0x8622,
        GL_VERTEX_ATTRIB_ARRAY_SIZE       = 0x8623,
        GL_VERTEX_ATTRIB_ARRAY_STRIDE     = 0x8624,
        GL_VERTEX_ATTRIB_ARRAY_TYPE       = 0x8625,
        GL_VERTEX_ATTRIB_ARRAY_NORMALIZED = 0x886A,
        GL_CURRENT_VERTEX_ATTRIB          = 0x8626;

    int GL_VERTEX_ATTRIB_ARRAY_POINTER = 0x8645;

    int GL_FRAGMENT_SHADER = 0x8B30;

    int GL_MAX_FRAGMENT_UNIFORM_COMPONENTS = 0x8B49;

    int GL_FRAGMENT_SHADER_DERIVATIVE_HINT = 0x8B8B;

    int
        GL_MAX_DRAW_BUFFERS = 0x8824,
        GL_DRAW_BUFFER0     = 0x8825,
        GL_DRAW_BUFFER1     = 0x8826,
        GL_DRAW_BUFFER2     = 0x8827,
        GL_DRAW_BUFFER3     = 0x8828,
        GL_DRAW_BUFFER4     = 0x8829,
        GL_DRAW_BUFFER5     = 0x882A,
        GL_DRAW_BUFFER6     = 0x882B,
        GL_DRAW_BUFFER7     = 0x882C,
        GL_DRAW_BUFFER8     = 0x882D,
        GL_DRAW_BUFFER9     = 0x882E,
        GL_DRAW_BUFFER10    = 0x882F,
        GL_DRAW_BUFFER11    = 0x8830,
        GL_DRAW_BUFFER12    = 0x8831,
        GL_DRAW_BUFFER13    = 0x8832,
        GL_DRAW_BUFFER14    = 0x8833,
        GL_DRAW_BUFFER15    = 0x8834;

    int GL_POINT_SPRITE = 0x8861;

    int GL_COORD_REPLACE = 0x8862;

    int GL_POINT_SPRITE_COORD_ORIGIN = 0x8CA0;

    int
        GL_LOWER_LEFT = 0x8CA1,
        GL_UPPER_LEFT = 0x8CA2;

    int
        GL_BLEND_EQUATION_RGB   = 0x8009,
        GL_BLEND_EQUATION_ALPHA = 0x883D;

    int
        GL_STENCIL_BACK_FUNC            = 0x8800,
        GL_STENCIL_BACK_FAIL            = 0x8801,
        GL_STENCIL_BACK_PASS_DEPTH_FAIL = 0x8802,
        GL_STENCIL_BACK_PASS_DEPTH_PASS = 0x8803,
        GL_STENCIL_BACK_REF             = 0x8CA3,
        GL_STENCIL_BACK_VALUE_MASK      = 0x8CA4,
        GL_STENCIL_BACK_WRITEMASK       = 0x8CA5;

    // GL 1.1 Functions

    void glEnable(int target);
    void glDisable(int target);
    void glAccum(int op, float value);
    void glAlphaFunc(int func, float ref);
    boolean glAreTexturesResident(IntBuffer textures, ByteBuffer residences);
    boolean glAreTexturesResident(int texture, ByteBuffer residences);
    void glArrayElement(int i);
    void glBegin(int mode);
    void glBindTexture(int target, int texture);
    void glBitmap(int w, int h, float xOrig, float yOrig, float xInc, float yInc, ByteBuffer data);
    void glBitmap(int w, int h, float xOrig, float yOrig, float xInc, float yInc, long data);
    void glBlendFunc(int sfactor, int dfactor);
    void glCallList(int list);
    void glCallLists(int type, ByteBuffer lists);
    void glCallLists(ByteBuffer lists);
    void glCallLists(ShortBuffer lists);
    void glCallLists(IntBuffer lists);
    void glClear(int mask);
    void glClearAccum(float red, float green, float blue, float alpha);
    void glClearColor(float red, float green, float blue, float alpha);
    void glClearDepth(double depth);
    void glClearIndex(float index);
    void glClearStencil(int s);
    void glClipPlane(int plane, DoubleBuffer equation);
    void glColor3b(byte red, byte green, byte blue);
    void glColor3s(short red, short green, short blue);
    void glColor3i(int red, int green, int blue);
    void glColor3f(float red, float green, float blue);
    void glColor3d(double red, double green, double blue);
    void glColor3ub(byte red, byte green, byte blue);
    void glColor3us(short red, short green, short blue);
    void glColor3ui(int red, int green, int blue);
    void glColor3bv(ByteBuffer v);
    void glColor3sv(ShortBuffer v);
    void glColor3iv(IntBuffer v);
    void glColor3fv(FloatBuffer v);
    void glColor3dv(DoubleBuffer v);
    void glColor3ubv(ByteBuffer v);
    void glColor3usv(ShortBuffer v);
    void glColor3uiv(IntBuffer v);
    void glColor4b(byte red, byte green, byte blue, byte alpha);
    void glColor4s(short red, short green, short blue, short alpha);
    void glColor4i(int red, int green, int blue, int alpha);
    void glColor4f(float red, float green, float blue, float alpha);
    void glColor4d(double red, double green, double blue, double alpha);
    void glColor4ub(byte red, byte green, byte blue, byte alpha);
    void glColor4us(short red, short green, short blue, short alpha);
    void glColor4ui(int red, int green, int blue, int alpha);
    void glColor4bv(ByteBuffer v);
    void glColor4sv(ShortBuffer v);
    void glColor4iv(IntBuffer v);
    void glColor4fv(FloatBuffer v);
    void glColor4dv(DoubleBuffer v);
    void glColor4ubv(ByteBuffer v);
    void glColor4usv(ShortBuffer v);
    void glColor4uiv(IntBuffer v);
    void glColorMask(boolean red, boolean green, boolean blue, boolean alpha);
    void glColorMaterial(int face, int mode);
    void glColorPointer(int size, int type, int stride, ByteBuffer pointer);
    void glColorPointer(int size, int type, int stride, long pointer);
    void glColorPointer(int size, int type, int stride, ShortBuffer pointer);
    void glColorPointer(int size, int type, int stride, IntBuffer pointer);
    void glColorPointer(int size, int type, int stride, FloatBuffer pointer);
    void glCopyPixels(int x, int y, int width, int height, int type);
    void glCullFace(int mode);
    void glDeleteLists(int list, int range);
    void glDepthFunc(int func);
    void glDepthMask(boolean flag);
    void glDepthRange(double zNear, double zFar);
    void glDisableClientState(int cap);
    void glDrawArrays(int mode, int first, int count);
    void glDrawBuffer(int buf);
    void glDrawElements(int mode, int count, int type, long indices);
    void glDrawElements(int mode, int type, ByteBuffer indices);
    void glDrawElements(int mode, ByteBuffer indices);
    void glDrawElements(int mode, ShortBuffer indices);
    void glDrawElements(int mode, IntBuffer indices);
    void glDrawPixels(int width, int height, int format, int type, ByteBuffer pixels);
    void glDrawPixels(int width, int height, int format, int type, long pixels);
    void glDrawPixels(int width, int height, int format, int type, ShortBuffer pixels);
    void glDrawPixels(int width, int height, int format, int type, IntBuffer pixels);
    void glDrawPixels(int width, int height, int format, int type, FloatBuffer pixels);
    void glEdgeFlag(boolean flag);
    void glEdgeFlagv(ByteBuffer flag);
    void glEdgeFlagPointer(int stride, ByteBuffer pointer);
    void glEdgeFlagPointer(int stride, long pointer);
    void glEnableClientState(int cap);
    void glEnd();
    void glEvalCoord1f(float u);
    void glEvalCoord1fv(FloatBuffer u);
    void glEvalCoord1d(double u);
    void glEvalCoord1dv(DoubleBuffer u);
    void glEvalCoord2f(float u, float v);
    void glEvalCoord2fv(FloatBuffer u);
    void glEvalCoord2d(double u, double v);
    void glEvalCoord2dv(DoubleBuffer u);
    void glEvalMesh1(int mode, int i1, int i2);
    void glEvalMesh2(int mode, int i1, int i2, int j1, int j2);
    void glEvalPoint1(int i);
    void glEvalPoint2(int i, int j);
    void glFeedbackBuffer(int type, FloatBuffer buffer);
    void glFinish();
    void glFlush();
    void glFogi(int pname, int param);
    void glFogiv(int pname, IntBuffer params);
    void glFogf(int pname, float param);
    void glFogfv(int pname, FloatBuffer params);
    void glFrontFace(int dir);
    int glGenLists(int s);
    void glGenTextures(IntBuffer textures);
    int glGenTextures();
    void glDeleteTextures(IntBuffer textures);
    void glDeleteTextures(int texture);
    void glGetClipPlane(int plane, DoubleBuffer equation);
    void glGetBooleanv(int pname, ByteBuffer params);
    boolean glGetBoolean(int pname);
    void glGetFloatv(int pname, FloatBuffer params);
    float glGetFloat(int pname);
    void glGetIntegerv(int pname, IntBuffer params);
    int glGetInteger(int pname);
    void glGetDoublev(int pname, DoubleBuffer params);
    double glGetDouble(int pname);
    int glGetError();
    void glGetLightiv(int light, int pname, IntBuffer data);
    int glGetLighti(int light, int pname);
    void glGetLightfv(int light, int pname, FloatBuffer data);
    float glGetLightf(int light, int pname);
    void glGetMapiv(int target, int query, IntBuffer data);
    int glGetMapi(int target, int query);
    void glGetMapfv(int target, int query, FloatBuffer data);
    float glGetMapf(int target, int query);
    void glGetMapdv(int target, int query, DoubleBuffer data);
    double glGetMapd(int target, int query);
    void glGetMaterialiv(int face, int pname, IntBuffer data);
    void glGetMaterialfv(int face, int pname, FloatBuffer data);
    void glGetPixelMapfv(int map, FloatBuffer data);
    void glGetPixelMapfv(int map, long data);
    void glGetPixelMapusv(int map, ShortBuffer data);
    void glGetPixelMapusv(int map, long data);
    void glGetPixelMapuiv(int map, IntBuffer data);
    void glGetPixelMapuiv(int map, long data);
    void glGetPointerv(int pname, LongBuffer params);
    long glGetPointer(int pname);
    void glGetPolygonStipple(ByteBuffer pattern);
    void glGetPolygonStipple(long pattern);
    String glGetString(int name);
    void glGetTexEnviv(int env, int pname, IntBuffer data);
    int glGetTexEnvi(int env, int pname);
    void glGetTexEnvfv(int env, int pname, FloatBuffer data);
    float glGetTexEnvf(int env, int pname);
    void glGetTexGeniv(int coord, int pname, IntBuffer data);
    int glGetTexGeni(int coord, int pname);
    void glGetTexGenfv(int coord, int pname, FloatBuffer data);
    float glGetTexGenf(int coord, int pname);
    void glGetTexGendv(int coord, int pname, DoubleBuffer data);
    double glGetTexGend(int coord, int pname);
    void glGetTexImage(int tex, int level, int format, int type, ByteBuffer pixels);
    void glGetTexImage(int tex, int level, int format, int type, long pixels);
    void glGetTexImage(int tex, int level, int format, int type, ShortBuffer pixels);
    void glGetTexImage(int tex, int level, int format, int type, IntBuffer pixels);
    void glGetTexImage(int tex, int level, int format, int type, FloatBuffer pixels);
    void glGetTexImage(int tex, int level, int format, int type, DoubleBuffer pixels);
    void glGetTexLevelParameteriv(int target, int level, int pname, IntBuffer params);
    int glGetTexLevelParameteri(int target, int level, int pname);
    void glGetTexLevelParameterfv(int target, int level, int pname, FloatBuffer params);
    float glGetTexLevelParameterf(int target, int level, int pname);
    void glGetTexParameteriv(int target, int pname, IntBuffer params);
    int glGetTexParameteri(int target, int pname);
    void glGetTexParameterfv(int target, int pname, FloatBuffer params);
    float glGetTexParameterf(int target, int pname);
    void glHint(int target, int hint);
    void glIndexi(int index);
    void glIndexub(byte index);
    void glIndexs(short index);
    void glIndexf(float index);
    void glIndexd(double index);
    void glIndexiv(IntBuffer index);
    void glIndexubv(ByteBuffer index);
    void glIndexsv(ShortBuffer index);
    void glIndexfv(FloatBuffer index);
    void glIndexdv(DoubleBuffer index);
    void glIndexMask(int mask);
    void glIndexPointer(int type, int stride, ByteBuffer pointer);
    void glIndexPointer(int type, int stride, long pointer);
    void glIndexPointer(int stride, ByteBuffer pointer);
    void glIndexPointer(int stride, ShortBuffer pointer);
    void glIndexPointer(int stride, IntBuffer pointer);
    void glInitNames();
    void glInterleavedArrays(int format, int stride, ByteBuffer pointer);
    void glInterleavedArrays(int format, int stride, long pointer);
    void glInterleavedArrays(int format, int stride, ShortBuffer pointer);
    void glInterleavedArrays(int format, int stride, IntBuffer pointer);
    void glInterleavedArrays(int format, int stride, FloatBuffer pointer);
    void glInterleavedArrays(int format, int stride, DoubleBuffer pointer);
    boolean glIsEnabled(int cap);
    boolean glIsList(int list);
    boolean glIsTexture(int texture);
    void glLightModeli(int pname, int param);
    void glLightModelf(int pname, float param);
    void glLightModeliv(int pname, IntBuffer params);
    void glLightModelfv(int pname, FloatBuffer params);
    void glLighti(int light, int pname, int param);
    void glLightf(int light, int pname, float param);
    void glLightiv(int light, int pname, IntBuffer params);
    void glLightfv(int light, int pname, FloatBuffer params);
    void glLineStipple(int factor, short pattern);
    void glLineWidth(float width);
    void glListBase(int base);
    void glLoadMatrixf(FloatBuffer m);
    void glLoadMatrixd(DoubleBuffer m);
    void glLoadIdentity();
    void glLoadName(int name);
    void glLogicOp(int op);
    void glMap1f(int target, float u1, float u2, int stride, int order, FloatBuffer points);
    void glMap1d(int target, double u1, double u2, int stride, int order, DoubleBuffer points);
    void glMap2f(
        int target,
        float u1,
        float u2,
        int ustride,
        int uorder,
        float v1,
        float v2,
        int vstride,
        int vorder,
        FloatBuffer points
    );
    void glMap2d(
        int target,
        double u1,
        double u2,
        int ustride,
        int uorder,
        double v1,
        double v2,
        int vstride,
        int vorder,
        DoubleBuffer points
    );
    void glMapGrid1f(int n, float u1, float u2);
    void glMapGrid1d(int n, double u1, double u2);
    void glMapGrid2f(int un, float u1, float u2, int vn, float v1, float v2);
    void glMapGrid2d(int un, double u1, double u2, int vn, double v1, double v2);
    void glMateriali(int face, int pname, int param);
    void glMaterialf(int face, int pname, float param);
    void glMaterialiv(int face, int pname, IntBuffer params);
    void glMaterialfv(int face, int pname, FloatBuffer params);
    void glMatrixMode(int mode);
    void glMultMatrixf(FloatBuffer m);
    void glMultMatrixd(DoubleBuffer m);
    void glFrustum(double l, double r, double b, double t, double n, double f);
    void glNewList(int n, int mode);
    void glEndList();
    void glNormal3f(float nx, float ny, float nz);
    void glNormal3b(byte nx, byte ny, byte nz);
    void glNormal3s(short nx, short ny, short nz);
    void glNormal3i(int nx, int ny, int nz);
    void glNormal3d(double nx, double ny, double nz);
    void glNormal3fv(FloatBuffer v);
    void glNormal3bv(ByteBuffer v);
    void glNormal3sv(ShortBuffer v);
    void glNormal3iv(IntBuffer v);
    void glNormal3dv(DoubleBuffer v);
    void glNormalPointer(int type, int stride, ByteBuffer pointer);
    void glNormalPointer(int type, int stride, long pointer);
    void glNormalPointer(int type, int stride, ShortBuffer pointer);
    void glNormalPointer(int type, int stride, IntBuffer pointer);
    void glNormalPointer(int type, int stride, FloatBuffer pointer);
    void glOrtho(double l, double r, double b, double t, double n, double f);
    void glPassThrough(float token);
    void glPixelMapfv(int map, int size, long values);
    void glPixelMapfv(int map, FloatBuffer values);
    void glPixelMapusv(int map, int size, long values);
    void glPixelMapusv(int map, ShortBuffer values);
    void glPixelMapuiv(int map, int size, long values);
    void glPixelMapuiv(int map, IntBuffer values);
    void glPixelStorei(int pname, int param);
    void glPixelStoref(int pname, float param);
    void glPixelTransferi(int pname, int param);
    void glPixelTransferf(int pname, float param);
    void glPixelZoom(float xfactor, float yfactor);
    void glPointSize(float size);
    void glPolygonMode(int face, int mode);
    void glPolygonOffset(float factor, float units);
    void glPolygonStipple(ByteBuffer pattern);
    void glPolygonStipple(long pattern);
    void glPushAttrib(int mask);
    void glPushClientAttrib(int mask);
    void glPopAttrib();
    void glPopClientAttrib();
    void glPopMatrix();
    void glPopName();
    void glPrioritizeTextures(IntBuffer textures, FloatBuffer priorities);
    void glPushMatrix();
    void glPushName(int name);
    void glRasterPos2i(int x, int y);
    void glRasterPos2s(short x, short y);
    void glRasterPos2f(float x, float y);
    void glRasterPos2d(double x, double y);
    void glRasterPos2iv(IntBuffer coords);
    void glRasterPos2sv(ShortBuffer coords);
    void glRasterPos2fv(FloatBuffer coords);
    void glRasterPos2dv(DoubleBuffer coords);
    void glRasterPos3i(int x, int y, int z);
    void glRasterPos3s(short x, short y, short z);
    void glRasterPos3f(float x, float y, float z);
    void glRasterPos3d(double x, double y, double z);
    void glRasterPos3iv(IntBuffer coords);
    void glRasterPos3sv(ShortBuffer coords);
    void glRasterPos3fv(FloatBuffer coords);
    void glRasterPos3dv(DoubleBuffer coords);
    void glRasterPos4i(int x, int y, int z, int w);
    void glRasterPos4s(short x, short y, short z, short w);
    void glRasterPos4f(float x, float y, float z, float w);
    void glRasterPos4d(double x, double y, double z, double w);
    void glRasterPos4iv(IntBuffer coords);
    void glRasterPos4sv(ShortBuffer coords);
    void glRasterPos4fv(FloatBuffer coords);
    void glRasterPos4dv(DoubleBuffer coords);
    void glReadBuffer(int src);
    void glReadPixels(int x, int y, int width, int height, int format, int type, ByteBuffer pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, long pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, ShortBuffer pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, IntBuffer pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, FloatBuffer pixels);
    void glRecti(int x1, int y1, int x2, int y2);
    void glRects(short x1, short y1, short x2, short y2);
    void glRectf(float x1, float y1, float x2, float y2);
    void glRectd(double x1, double y1, double x2, double y2);
    void glRectiv(IntBuffer v1, IntBuffer v2);
    void glRectsv(ShortBuffer v1, ShortBuffer v2);
    void glRectfv(FloatBuffer v1, FloatBuffer v2);
    void glRectdv(DoubleBuffer v1, DoubleBuffer v2);
    int glRenderMode(int mode);
    void glRotatef(float angle, float x, float y, float z);
    void glRotated(double angle, double x, double y, double z);
    void glScalef(float x, float y, float z);
    void glScaled(double x, double y, double z);
    void glScissor(int x, int y, int width, int height);
    void glSelectBuffer(IntBuffer buffer);
    void glShadeModel(int mode);
    void glStencilFunc(int func, int ref, int mask);
    void glStencilMask(int mask);
    void glStencilOp(int sfail, int dpfail, int dppass);
    void glTexCoord1f(float s);
    void glTexCoord1s(short s);
    void glTexCoord1i(int s);
    void glTexCoord1d(double s);
    void glTexCoord1fv(FloatBuffer v);
    void glTexCoord1sv(ShortBuffer v);
    void glTexCoord1iv(IntBuffer v);
    void glTexCoord1dv(DoubleBuffer v);
    void glTexCoord2f(float s, float t);
    void glTexCoord2s(short s, short t);
    void glTexCoord2i(int s, int t);
    void glTexCoord2d(double s, double t);
    void glTexCoord2fv(FloatBuffer v);
    void glTexCoord2sv(ShortBuffer v);
    void glTexCoord2iv(IntBuffer v);
    void glTexCoord2dv(DoubleBuffer v);
    void glTexCoord3f(float s, float t, float r);
    void glTexCoord3s(short s, short t, short r);
    void glTexCoord3i(int s, int t, int r);
    void glTexCoord3d(double s, double t, double r);
    void glTexCoord3fv(FloatBuffer v);
    void glTexCoord3sv(ShortBuffer v);
    void glTexCoord3iv(IntBuffer v);
    void glTexCoord3dv(DoubleBuffer v);
    void glTexCoord4f(float s, float t, float r, float q);
    void glTexCoord4s(short s, short t, short r, short q);
    void glTexCoord4i(int s, int t, int r, int q);
    void glTexCoord4d(double s, double t, double r, double q);
    void glTexCoord4fv(FloatBuffer v);
    void glTexCoord4sv(ShortBuffer v);
    void glTexCoord4iv(IntBuffer v);
    void glTexCoord4dv(DoubleBuffer v);
    void glTexCoordPointer(int size, int type, int stride, ByteBuffer pointer);
    void glTexCoordPointer(int size, int type, int stride, long pointer);
    void glTexCoordPointer(int size, int type, int stride, ShortBuffer pointer);
    void glTexCoordPointer(int size, int type, int stride, IntBuffer pointer);
    void glTexCoordPointer(int size, int type, int stride, FloatBuffer pointer);
    void glTexEnvi(int target, int pname, int param);
    void glTexEnviv(int target, int pname, IntBuffer params);
    void glTexEnvf(int target, int pname, float param);
    void glTexEnvfv(int target, int pname, FloatBuffer params);
    void glTexGeni(int coord, int pname, int param);
    void glTexGeniv(int coord, int pname, IntBuffer params);
    void glTexGenf(int coord, int pname, float param);
    void glTexGenfv(int coord, int pname, FloatBuffer params);
    void glTexGend(int coord, int pname, double param);
    void glTexGendv(int coord, int pname, DoubleBuffer params);
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        ByteBuffer pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        long pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        ShortBuffer pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        IntBuffer pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        FloatBuffer pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        DoubleBuffer pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        ByteBuffer pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        long pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        ShortBuffer pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        IntBuffer pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        FloatBuffer pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        DoubleBuffer pixels
    );
    void glCopyTexImage1D(int target, int level, int internalFormat, int x, int y, int width, int border);
    void glCopyTexImage2D(int target, int level, int internalFormat, int x, int y, int width, int height, int border);
    void glCopyTexSubImage1D(int target, int level, int xoffset, int x, int y, int width);
    void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height);
    void glTexParameteri(int target, int pname, int param);
    void glTexParameteriv(int target, int pname, IntBuffer params);
    void glTexParameterf(int target, int pname, float param);
    void glTexParameterfv(int target, int pname, FloatBuffer params);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ByteBuffer pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, long pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, ShortBuffer pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, IntBuffer pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, FloatBuffer pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, DoubleBuffer pixels);
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        ByteBuffer pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        long pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        ShortBuffer pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        IntBuffer pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        FloatBuffer pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        DoubleBuffer pixels
    );
    void glTranslatef(float x, float y, float z);
    void glTranslated(double x, double y, double z);
    void glVertex2f(float x, float y);
    void glVertex2s(short x, short y);
    void glVertex2i(int x, int y);
    void glVertex2d(double x, double y);
    void glVertex2fv(FloatBuffer coords);
    void glVertex2sv(ShortBuffer coords);
    void glVertex2iv(IntBuffer coords);
    void glVertex2dv(DoubleBuffer coords);
    void glVertex3f(float x, float y, float z);
    void glVertex3s(short x, short y, short z);
    void glVertex3i(int x, int y, int z);
    void glVertex3d(double x, double y, double z);
    void glVertex3fv(FloatBuffer coords);
    void glVertex3sv(ShortBuffer coords);
    void glVertex3iv(IntBuffer coords);
    void glVertex3dv(DoubleBuffer coords);
    void glVertex4f(float x, float y, float z, float w);
    void glVertex4s(short x, short y, short z, short w);
    void glVertex4i(int x, int y, int z, int w);
    void glVertex4d(double x, double y, double z, double w);
    void glVertex4fv(FloatBuffer coords);
    void glVertex4sv(ShortBuffer coords);
    void glVertex4iv(IntBuffer coords);
    void glVertex4dv(DoubleBuffer coords);
    void glVertexPointer(int size, int type, int stride, ByteBuffer pointer);
    void glVertexPointer(int size, int type, int stride, long pointer);
    void glVertexPointer(int size, int type, int stride, ShortBuffer pointer);
    void glVertexPointer(int size, int type, int stride, IntBuffer pointer);
    void glVertexPointer(int size, int type, int stride, FloatBuffer pointer);
    void glViewport(int x, int y, int w, int h);
    boolean glAreTexturesResident(int[] textures, ByteBuffer residences);
    void glClipPlane(int plane, double[] equation);
    void glColor3sv(short[] v);
    void glColor3iv(int[] v);
    void glColor3fv(float[] v);
    void glColor3dv(double[] v);
    void glColor3usv(short[] v);
    void glColor3uiv(int[] v);
    void glColor4sv(short[] v);
    void glColor4iv(int[] v);
    void glColor4fv(float[] v);
    void glColor4dv(double[] v);
    void glColor4usv(short[] v);
    void glColor4uiv(int[] v);
    void glDrawPixels(int width, int height, int format, int type, short[] pixels);
    void glDrawPixels(int width, int height, int format, int type, int[] pixels);
    void glDrawPixels(int width, int height, int format, int type, float[] pixels);
    void glEvalCoord1fv(float[] u);
    void glEvalCoord1dv(double[] u);
    void glEvalCoord2fv(float[] u);
    void glEvalCoord2dv(double[] u);
    void glFeedbackBuffer(int type, float[] buffer);
    void glFogiv(int pname, int[] params);
    void glFogfv(int pname, float[] params);
    void glGenTextures(int[] textures);
    void glDeleteTextures(int[] textures);
    void glGetClipPlane(int plane, double[] equation);
    void glGetFloatv(int pname, float[] params);
    void glGetIntegerv(int pname, int[] params);
    void glGetDoublev(int pname, double[] params);
    void glGetLightiv(int light, int pname, int[] data);
    void glGetLightfv(int light, int pname, float[] data);
    void glGetMapiv(int target, int query, int[] data);
    void glGetMapfv(int target, int query, float[] data);
    void glGetMapdv(int target, int query, double[] data);
    void glGetMaterialiv(int face, int pname, int[] data);
    void glGetMaterialfv(int face, int pname, float[] data);
    void glGetPixelMapfv(int map, float[] data);
    void glGetPixelMapusv(int map, short[] data);
    void glGetPixelMapuiv(int map, int[] data);
    void glGetTexEnviv(int env, int pname, int[] data);
    void glGetTexEnvfv(int env, int pname, float[] data);
    void glGetTexGeniv(int coord, int pname, int[] data);
    void glGetTexGenfv(int coord, int pname, float[] data);
    void glGetTexGendv(int coord, int pname, double[] data);
    void glGetTexImage(int tex, int level, int format, int type, short[] pixels);
    void glGetTexImage(int tex, int level, int format, int type, int[] pixels);
    void glGetTexImage(int tex, int level, int format, int type, float[] pixels);
    void glGetTexImage(int tex, int level, int format, int type, double[] pixels);
    void glGetTexLevelParameteriv(int target, int level, int pname, int[] params);
    void glGetTexLevelParameterfv(int target, int level, int pname, float[] params);
    void glGetTexParameteriv(int target, int pname, int[] params);
    void glGetTexParameterfv(int target, int pname, float[] params);
    void glIndexiv(int[] index);
    void glIndexsv(short[] index);
    void glIndexfv(float[] index);
    void glIndexdv(double[] index);
    void glInterleavedArrays(int format, int stride, short[] pointer);
    void glInterleavedArrays(int format, int stride, int[] pointer);
    void glInterleavedArrays(int format, int stride, float[] pointer);
    void glInterleavedArrays(int format, int stride, double[] pointer);
    void glLightModeliv(int pname, int[] params);
    void glLightModelfv(int pname, float[] params);
    void glLightiv(int light, int pname, int[] params);
    void glLightfv(int light, int pname, float[] params);
    void glLoadMatrixf(float[] m);
    void glLoadMatrixd(double[] m);
    void glMap1f(int target, float u1, float u2, int stride, int order, float[] points);
    void glMap1d(int target, double u1, double u2, int stride, int order, double[] points);
    void glMap2f(
        int target,
        float u1,
        float u2,
        int ustride,
        int uorder,
        float v1,
        float v2,
        int vstride,
        int vorder,
        float[] points
    );
    void glMap2d(
        int target,
        double u1,
        double u2,
        int ustride,
        int uorder,
        double v1,
        double v2,
        int vstride,
        int vorder,
        double[] points
    );
    void glMaterialiv(int face, int pname, int[] params);
    void glMaterialfv(int face, int pname, float[] params);
    void glMultMatrixf(float[] m);
    void glMultMatrixd(double[] m);
    void glNormal3fv(float[] v);
    void glNormal3sv(short[] v);
    void glNormal3iv(int[] v);
    void glNormal3dv(double[] v);
    void glPixelMapfv(int map, float[] values);
    void glPixelMapusv(int map, short[] values);
    void glPixelMapuiv(int map, int[] values);
    void glPrioritizeTextures(int[] textures, float[] priorities);
    void glRasterPos2iv(int[] coords);
    void glRasterPos2sv(short[] coords);
    void glRasterPos2fv(float[] coords);
    void glRasterPos2dv(double[] coords);
    void glRasterPos3iv(int[] coords);
    void glRasterPos3sv(short[] coords);
    void glRasterPos3fv(float[] coords);
    void glRasterPos3dv(double[] coords);
    void glRasterPos4iv(int[] coords);
    void glRasterPos4sv(short[] coords);
    void glRasterPos4fv(float[] coords);
    void glRasterPos4dv(double[] coords);
    void glReadPixels(int x, int y, int width, int height, int format, int type, short[] pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, int[] pixels);
    void glReadPixels(int x, int y, int width, int height, int format, int type, float[] pixels);
    void glRectiv(int[] v1, int[] v2);
    void glRectsv(short[] v1, short[] v2);
    void glRectfv(float[] v1, float[] v2);
    void glRectdv(double[] v1, double[] v2);
    void glSelectBuffer(int[] buffer);
    void glTexCoord1fv(float[] v);
    void glTexCoord1sv(short[] v);
    void glTexCoord1iv(int[] v);
    void glTexCoord1dv(double[] v);
    void glTexCoord2fv(float[] v);
    void glTexCoord2sv(short[] v);
    void glTexCoord2iv(int[] v);
    void glTexCoord2dv(double[] v);
    void glTexCoord3fv(float[] v);
    void glTexCoord3sv(short[] v);
    void glTexCoord3iv(int[] v);
    void glTexCoord3dv(double[] v);
    void glTexCoord4fv(float[] v);
    void glTexCoord4sv(short[] v);
    void glTexCoord4iv(int[] v);
    void glTexCoord4dv(double[] v);
    void glTexEnviv(int target, int pname, int[] params);
    void glTexEnvfv(int target, int pname, float[] params);
    void glTexGeniv(int coord, int pname, int[] params);
    void glTexGenfv(int coord, int pname, float[] params);
    void glTexGendv(int coord, int pname, double[] params);
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        short[] pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        int[] pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        float[] pixels
    );
    void glTexImage1D(
        int target,
        int level,
        int internalformat,
        int width,
        int border,
        int format,
        int type,
        double[] pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        short[] pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        int[] pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        float[] pixels
    );
    void glTexImage2D(
        int target,
        int level,
        int internalformat,
        int width,
        int height,
        int border,
        int format,
        int type,
        double[] pixels
    );
    void glTexParameteriv(int target, int pname, int[] params);
    void glTexParameterfv(int target, int pname, float[] params);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, short[] pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, int[] pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, float[] pixels);
    void glTexSubImage1D(int target, int level, int xoffset, int width, int format, int type, double[] pixels);
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        short[] pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        int[] pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        float[] pixels
    );
    void glTexSubImage2D(
        int target,
        int level,
        int xoffset,
        int yoffset,
        int width,
        int height,
        int format,
        int type,
        double[] pixels
    );
    void glVertex2fv(float[] coords);
    void glVertex2sv(short[] coords);
    void glVertex2iv(int[] coords);
    void glVertex2dv(double[] coords);
    void glVertex3fv(float[] coords);
    void glVertex3sv(short[] coords);
    void glVertex3iv(int[] coords);
    void glVertex3dv(double[] coords);
    void glVertex4fv(float[] coords);
    void glVertex4sv(short[] coords);
    void glVertex4iv(int[] coords);
    void glVertex4dv(double[] coords);

    // GL 1.2 Functions

    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, long pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, ShortBuffer pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, IntBuffer pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, FloatBuffer pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, DoubleBuffer pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ShortBuffer pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, IntBuffer pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, FloatBuffer pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, DoubleBuffer pixels);
    void glCopyTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int x, int y, int width, int height);
    void glDrawRangeElements(int mode, int start, int end, int count, int type, long indices);
    void glDrawRangeElements(int mode, int start, int end, int type, ByteBuffer indices);
    void glDrawRangeElements(int mode, int start, int end, ByteBuffer indices);
    void glDrawRangeElements(int mode, int start, int end, ShortBuffer indices);
    void glDrawRangeElements(int mode, int start, int end, IntBuffer indices);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, short [] pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, int [] pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, float [] pixels);
    void glTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int format, int type, double [] pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, short[] pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int[] pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, float[] pixels);
    void glTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, double[] pixels);

    // GL 1.3 Functions

    void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, int imageSize, long data);
    void glCompressedTexImage3D(int target, int level, int internalformat, int width, int height, int depth, int border, ByteBuffer data);
    void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, long data);
    void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, ByteBuffer data);
    void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, int imageSize, long data);
    void glCompressedTexImage1D(int target, int level, int internalformat, int width, int border, ByteBuffer data);
    void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int imageSize, long data);
    void glCompressedTexSubImage3D(int target, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, ByteBuffer data);
    void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, long data);
    void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, ByteBuffer data);
    void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, int imageSize, long data);
    void glCompressedTexSubImage1D(int target, int level, int xoffset, int width, int format, ByteBuffer data);
    void glGetCompressedTexImage(int target, int level, ByteBuffer pixels);
    void glGetCompressedTexImage(int target, int level, long pixels);
    void glSampleCoverage(float value, boolean invert);
    void glActiveTexture(int texture);
    void glClientActiveTexture(int texture);
    void glMultiTexCoord1f(int texture, float s);
    void glMultiTexCoord1s(int texture, short s);
    void glMultiTexCoord1i(int texture, int s);
    void glMultiTexCoord1d(int texture, double s);
    void glMultiTexCoord1fv(int texture, FloatBuffer v);
    void glMultiTexCoord1sv(int texture, ShortBuffer v);
    void glMultiTexCoord1iv(int texture, IntBuffer v);
    void glMultiTexCoord1dv(int texture, DoubleBuffer v);
    void glMultiTexCoord2f(int texture, float s, float t);
    void glMultiTexCoord2s(int texture, short s, short t);
    void glMultiTexCoord2i(int texture, int s, int t);
    void glMultiTexCoord2d(int texture, double s, double t);
    void glMultiTexCoord2fv(int texture, FloatBuffer v);
    void glMultiTexCoord2sv(int texture, ShortBuffer v);
    void glMultiTexCoord2iv(int texture, IntBuffer v);
    void glMultiTexCoord2dv(int texture, DoubleBuffer v);
    void glMultiTexCoord3f(int texture, float s, float t, float r);
    void glMultiTexCoord3s(int texture, short s, short t, short r);
    void glMultiTexCoord3i(int texture, int s, int t, int r);
    void glMultiTexCoord3d(int texture, double s, double t, double r);
    void glMultiTexCoord3fv(int texture, FloatBuffer v);
    void glMultiTexCoord3sv(int texture, ShortBuffer v);
    void glMultiTexCoord3iv(int texture, IntBuffer v);
    void glMultiTexCoord3dv(int texture, DoubleBuffer v);
    void glMultiTexCoord4f(int texture, float s, float t, float r, float q);
    void glMultiTexCoord4s(int texture, short s, short t, short r, short q);
    void glMultiTexCoord4i(int texture, int s, int t, int r, int q);
    void glMultiTexCoord4d(int texture, double s, double t, double r, double q);
    void glMultiTexCoord4fv(int texture, FloatBuffer v);
    void glMultiTexCoord4sv(int texture, ShortBuffer v);
    void glMultiTexCoord4iv(int texture, IntBuffer v);
    void glMultiTexCoord4dv(int texture, DoubleBuffer v);
    void glLoadTransposeMatrixf(FloatBuffer m);
    void glLoadTransposeMatrixd(DoubleBuffer m);
    void glMultTransposeMatrixf(FloatBuffer m);
    void glMultTransposeMatrixd(DoubleBuffer m);
    void glMultiTexCoord1fv(int texture, float[] v);
    void glMultiTexCoord1sv(int texture, short[] v);
    void glMultiTexCoord1iv(int texture, int[] v);
    void glMultiTexCoord1dv(int texture, double[] v);
    void glMultiTexCoord2fv(int texture, float[] v);
    void glMultiTexCoord2sv(int texture, short[] v);
    void glMultiTexCoord2iv(int texture, int[] v);
    void glMultiTexCoord2dv(int texture, double[] v);
    void glMultiTexCoord3fv(int texture, float[] v);
    void glMultiTexCoord3sv(int texture, short[] v);
    void glMultiTexCoord3iv(int texture, int[] v);
    void glMultiTexCoord3dv(int texture, double[] v);
    void glMultiTexCoord4fv(int texture, float[] v);
    void glMultiTexCoord4sv(int texture, short[] v);
    void glMultiTexCoord4iv(int texture, int[] v);
    void glMultiTexCoord4dv(int texture, double[] v);
    void glLoadTransposeMatrixf(float[] m);
    void glLoadTransposeMatrixd(double[] m);
    void glMultTransposeMatrixf(float[] m);
    void glMultTransposeMatrixd(double[] m);

    // GL 1.4 Functions

    void glBlendColor(float red, float green, float blue, float alpha);
    void glBlendEquation(int mode);
    void glFogCoordf(float coord);
    void glFogCoordd(double coord);
    void glFogCoordfv(FloatBuffer coord);
    void glFogCoorddv(DoubleBuffer coord);
    void glFogCoordPointer(int type, int stride, ByteBuffer pointer);
    void glFogCoordPointer(int type, int stride, long pointer);
    void glFogCoordPointer(int type, int stride, ShortBuffer pointer);
    void glFogCoordPointer(int type, int stride, FloatBuffer pointer);
    void glMultiDrawArrays(int mode, IntBuffer first, IntBuffer count);
    void glMultiDrawElements(int mode, IntBuffer count, int type, LongBuffer indices);
    void glPointParameterf(int pname, float param);
    void glPointParameteri(int pname, int param);
    void glPointParameterfv(int pname, FloatBuffer params);
    void glPointParameteriv(int pname, IntBuffer params);
    void glSecondaryColor3b(byte red, byte green, byte blue);
    void glSecondaryColor3s(short red, short green, short blue);
    void glSecondaryColor3i(int red, int green, int blue);
    void glSecondaryColor3f(float red, float green, float blue);
    void glSecondaryColor3d(double red, double green, double blue);
    void glSecondaryColor3ub(byte red, byte green, byte blue);
    void glSecondaryColor3us(short red, short green, short blue);
    void glSecondaryColor3ui(int red, int green, int blue);
    void glSecondaryColor3bv(ByteBuffer v);
    void glSecondaryColor3sv(ShortBuffer v);
    void glSecondaryColor3iv(IntBuffer v);
    void glSecondaryColor3fv(FloatBuffer v);
    void glSecondaryColor3dv(DoubleBuffer v);
    void glSecondaryColor3ubv(ByteBuffer v);
    void glSecondaryColor3usv(ShortBuffer v);
    void glSecondaryColor3uiv(IntBuffer v);
    void glSecondaryColorPointer(int size, int type, int stride, ByteBuffer pointer);
    void glSecondaryColorPointer(int size, int type, int stride, long pointer);
    void glSecondaryColorPointer(int size, int type, int stride, ShortBuffer pointer);
    void glSecondaryColorPointer(int size, int type, int stride, IntBuffer pointer);
    void glSecondaryColorPointer(int size, int type, int stride, FloatBuffer pointer);
    void glBlendFuncSeparate(int sfactorRGB, int dfactorRGB, int sfactorAlpha, int dfactorAlpha);
    void glWindowPos2i(int x, int y);
    void glWindowPos2s(short x, short y);
    void glWindowPos2f(float x, float y);
    void glWindowPos2d(double x, double y);
    void glWindowPos2iv(IntBuffer p);
    void glWindowPos2sv(ShortBuffer p);
    void glWindowPos2fv(FloatBuffer p);
    void glWindowPos2dv(DoubleBuffer p);
    void glWindowPos3i(int x, int y, int z);
    void glWindowPos3s(short x, short y, short z);
    void glWindowPos3f(float x, float y, float z);
    void glWindowPos3d(double x, double y, double z);
    void glWindowPos3iv(IntBuffer p);
    void glWindowPos3sv(ShortBuffer p);
    void glWindowPos3fv(FloatBuffer p);
    void glWindowPos3dv(DoubleBuffer p);
    void glFogCoordfv(float[] coord);
    void glFogCoorddv(double[] coord);
    void glMultiDrawArrays(int mode, int[] first, int[] count);
    void glMultiDrawElements(int mode, int[] count, int type, LongBuffer indices);
    void glPointParameterfv(int pname, float[] params);
    void glPointParameteriv(int pname, int[] params);
    void glSecondaryColor3sv(short[] v);
    void glSecondaryColor3iv(int[] v);
    void glSecondaryColor3fv(float[] v);
    void glSecondaryColor3dv(double[] v);
    void glSecondaryColor3usv(short[] v);
    void glSecondaryColor3uiv(int[] v);
    void glWindowPos2iv(int[] p);
    void glWindowPos2sv(short[] p);
    void glWindowPos2fv(float[] p);
    void glWindowPos2dv(double[] p);
    void glWindowPos3iv(int[] p);
    void glWindowPos3sv(short[] p);
    void glWindowPos3fv(float[] p);
    void glWindowPos3dv(double[] p);

    // GL 1.5 Functions

    void glBindBuffer(int target, int buffer);
    void glDeleteBuffers(IntBuffer buffers);
    void glDeleteBuffers(int buffer);
    void glGenBuffers(IntBuffer buffers);
    int glGenBuffers();
    boolean glIsBuffer(int buffer);
    void glBufferData(int target, long size, int usage);
    void glBufferData(int target, ByteBuffer data, int usage);
    void glBufferData(int target, ShortBuffer data, int usage);
    void glBufferData(int target, IntBuffer data, int usage);
    void glBufferData(int target, LongBuffer data, int usage);
    void glBufferData(int target, FloatBuffer data, int usage);
    void glBufferData(int target, DoubleBuffer data, int usage);
    void glBufferSubData(int target, long offset, ByteBuffer data);
    void glBufferSubData(int target, long offset, ShortBuffer data);
    void glBufferSubData(int target, long offset, IntBuffer data);
    void glBufferSubData(int target, long offset, LongBuffer data);
    void glBufferSubData(int target, long offset, FloatBuffer data);
    void glBufferSubData(int target, long offset, DoubleBuffer data);
    void glGetBufferSubData(int target, long offset, ByteBuffer data);
    void glGetBufferSubData(int target, long offset, ShortBuffer data);
    void glGetBufferSubData(int target, long offset, IntBuffer data);
    void glGetBufferSubData(int target, long offset, LongBuffer data);
    void glGetBufferSubData(int target, long offset, FloatBuffer data);
    void glGetBufferSubData(int target, long offset, DoubleBuffer data);
    ByteBuffer glMapBuffer(int target, int access);
    ByteBuffer glMapBuffer(int target, int access, ByteBuffer oldBuffer);
    ByteBuffer glMapBuffer(int target, int access, long length, ByteBuffer oldBuffer);
    boolean glUnmapBuffer(int target);
    void glGetBufferParameteriv(int target, int pname, IntBuffer params);
    int glGetBufferParameteri(int target, int pname);
    void glGetBufferPointerv(int target, int pname, LongBuffer params);
    long glGetBufferPointer(int target, int pname);
    void glGenQueries(IntBuffer ids);
    int glGenQueries();
    void glDeleteQueries(IntBuffer ids);
    void glDeleteQueries(int id);
    boolean glIsQuery(int id);
    void glBeginQuery(int target, int id);
    void glEndQuery(int target);
    void glGetQueryiv(int target, int pname, IntBuffer params);
    int glGetQueryi(int target, int pname);
    void glGetQueryObjectiv(int id, int pname, IntBuffer params);
    void glGetQueryObjectiv(int id, int pname, long params);
    int glGetQueryObjecti(int id, int pname);
    void glGetQueryObjectuiv(int id, int pname, IntBuffer params);
    void glGetQueryObjectuiv(int id, int pname, long params);
    int glGetQueryObjectui(int id, int pname);
    void glDeleteBuffers(int[] buffers);
    void glGenBuffers(int[] buffers);
    void glBufferData(int target, short[] data, int usage);
    void glBufferData(int target, int[] data, int usage);
    void glBufferData(int target, long[] data, int usage);
    void glBufferData(int target, float[] data, int usage);
    void glBufferData(int target, double[] data, int usage);
    void glBufferSubData(int target, long offset, short[] data);
    void glBufferSubData(int target, long offset, int[] data);
    void glBufferSubData(int target, long offset, long[] data);
    void glBufferSubData(int target, long offset, float[] data);
    void glBufferSubData(int target, long offset, double[] data);
    void glGetBufferSubData(int target, long offset, short[] data);
    void glGetBufferSubData(int target, long offset, int[] data);
    void glGetBufferSubData(int target, long offset, long[] data);
    void glGetBufferSubData(int target, long offset, float[] data);
    void glGetBufferSubData(int target, long offset, double[] data);
    void glGetBufferParameteriv(int target, int pname, int[] params);
    void glGenQueries(int[] ids);
    void glDeleteQueries(int[] ids);
    void glGetQueryiv(int target, int pname, int[] params);
    void glGetQueryObjectiv(int id, int pname, int[] params);
    void glGetQueryObjectuiv(int id, int pname, int[] params);

    // GL 2.0 Functions

    int glCreateProgram();
    void glDeleteProgram(int program);
    boolean glIsProgram(int program);
    int glCreateShader(int type);
    void glDeleteShader(int shader);
    boolean glIsShader(int shader);
    void glAttachShader(int program, int shader);
    void glDetachShader(int program, int shader);
    void glShaderSource(int shader, LongBuffer strings, IntBuffer length);
    void glShaderSource(int shader, CharSequence... strings);
    void glShaderSource(int shader, CharSequence string);
    void glCompileShader(int shader);
    void glLinkProgram(int program);
    void glUseProgram(int program);
    void glValidateProgram(int program);
    void glUniform1f(int location, float v0);
    void glUniform2f(int location, float v0, float v1);
    void glUniform3f(int location, float v0, float v1, float v2);
    void glUniform4f(int location, float v0, float v1, float v2, float v3);
    void glUniform1i(int location, int v0);
    void glUniform2i(int location, int v0, int v1);
    void glUniform3i(int location, int v0, int v1, int v2);
    void glUniform4i(int location, int v0, int v1, int v2, int v3);
    void glUniform1fv(int location, FloatBuffer value);
    void glUniform2fv(int location, FloatBuffer value);
    void glUniform3fv(int location, FloatBuffer value);
    void glUniform4fv(int location, FloatBuffer value);
    void glUniform1iv(int location, IntBuffer value);
    void glUniform2iv(int location, IntBuffer value);
    void glUniform3iv(int location, IntBuffer value);
    void glUniform4iv(int location, IntBuffer value);
    void glUniformMatrix2fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix3fv(int location, boolean transpose, FloatBuffer value);
    void glUniformMatrix4fv(int location, boolean transpose, FloatBuffer value);
    void glGetShaderiv(int shader, int pname, IntBuffer params);
    int glGetShaderi(int shader, int pname);
    void glGetProgramiv(int program, int pname, IntBuffer params);
    int glGetProgrami(int program, int pname);
    void glGetShaderInfoLog(int shader, IntBuffer length, ByteBuffer infoLog);
    String glGetShaderInfoLog(int shader, int maxLength);
    String glGetShaderInfoLog(int shader);
    void glGetProgramInfoLog(int program, IntBuffer length, ByteBuffer infoLog);
    String glGetProgramInfoLog(int program, int maxLength);
    String glGetProgramInfoLog(int program);
    void glGetAttachedShaders(int program, IntBuffer count, IntBuffer shaders);
    int glGetUniformLocation(int program, ByteBuffer name);
    int glGetUniformLocation(int program, CharSequence name);
    void glGetActiveUniform(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name);
    String glGetActiveUniform(int program, int index, int maxLength, IntBuffer size, IntBuffer type);
    String glGetActiveUniform(int program, int index, IntBuffer size, IntBuffer type);
    void glGetUniformfv(int program, int location, FloatBuffer params);
    float glGetUniformf(int program, int location);
    void glGetUniformiv(int program, int location, IntBuffer params);
    int glGetUniformi(int program, int location);
    void glGetShaderSource(int shader, IntBuffer length, ByteBuffer source);
    String glGetShaderSource(int shader, int maxLength);
    String glGetShaderSource(int shader);
    void glVertexAttrib1f(int index, float v0);
    void glVertexAttrib1s(int index, short v0);
    void glVertexAttrib1d(int index, double v0);
    void glVertexAttrib2f(int index, float v0, float v1);
    void glVertexAttrib2s(int index, short v0, short v1);
    void glVertexAttrib2d(int index, double v0, double v1);
    void glVertexAttrib3f(int index, float v0, float v1, float v2);
    void glVertexAttrib3s(int index, short v0, short v1, short v2);
    void glVertexAttrib3d(int index, double v0, double v1, double v2);
    void glVertexAttrib4f(int index, float v0, float v1, float v2, float v3);
    void glVertexAttrib4s(int index, short v0, short v1, short v2, short v3);
    void glVertexAttrib4d(int index, double v0, double v1, double v2, double v3);
    void glVertexAttrib4Nub(int index, byte x, byte y, byte z, byte w);
    void glVertexAttrib1fv(int index, FloatBuffer v);
    void glVertexAttrib1sv(int index, ShortBuffer v);
    void glVertexAttrib1dv(int index, DoubleBuffer v);
    void glVertexAttrib2fv(int index, FloatBuffer v);
    void glVertexAttrib2sv(int index, ShortBuffer v);
    void glVertexAttrib2dv(int index, DoubleBuffer v);
    void glVertexAttrib3fv(int index, FloatBuffer v);
    void glVertexAttrib3sv(int index, ShortBuffer v);
    void glVertexAttrib3dv(int index, DoubleBuffer v);
    void glVertexAttrib4fv(int index, FloatBuffer v);
    void glVertexAttrib4sv(int index, ShortBuffer v);
    void glVertexAttrib4dv(int index, DoubleBuffer v);
    void glVertexAttrib4iv(int index, IntBuffer v);
    void glVertexAttrib4bv(int index, ByteBuffer v);
    void glVertexAttrib4ubv(int index, ByteBuffer v);
    void glVertexAttrib4usv(int index, ShortBuffer v);
    void glVertexAttrib4uiv(int index, IntBuffer v);
    void glVertexAttrib4Nbv(int index, ByteBuffer v);
    void glVertexAttrib4Nsv(int index, ShortBuffer v);
    void glVertexAttrib4Niv(int index, IntBuffer v);
    void glVertexAttrib4Nubv(int index, ByteBuffer v);
    void glVertexAttrib4Nusv(int index, ShortBuffer v);
    void glVertexAttrib4Nuiv(int index, IntBuffer v);
    void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ByteBuffer pointer);
    void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, long pointer);
    void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, ShortBuffer pointer);
    void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, IntBuffer pointer);
    void glVertexAttribPointer(int index, int size, int type, boolean normalized, int stride, FloatBuffer pointer);
    void glEnableVertexAttribArray(int index);
    void glDisableVertexAttribArray(int index);
    void glBindAttribLocation(int program, int index, ByteBuffer name);
    void glBindAttribLocation(int program, int index, CharSequence name);
    void glGetActiveAttrib(int program, int index, IntBuffer length, IntBuffer size, IntBuffer type, ByteBuffer name);
    String glGetActiveAttrib(int program, int index, int maxLength, IntBuffer size, IntBuffer type);
    String glGetActiveAttrib(int program, int index, IntBuffer size, IntBuffer type);
    int glGetAttribLocation(int program, ByteBuffer name);
    int glGetAttribLocation(int program, CharSequence name);
    void glGetVertexAttribiv(int index, int pname, IntBuffer params);
    int glGetVertexAttribi(int index, int pname);
    void glGetVertexAttribfv(int index, int pname, FloatBuffer params);
    void glGetVertexAttribdv(int index, int pname, DoubleBuffer params);
    void glGetVertexAttribPointerv(int index, int pname, LongBuffer pointer);
    long glGetVertexAttribPointer(int index, int pname);
    void glDrawBuffers(IntBuffer bufs);
    void glDrawBuffers(int buf);
    void glBlendEquationSeparate(int modeRGB, int modeAlpha);
    void glStencilOpSeparate(int face, int sfail, int dpfail, int dppass);
    void glStencilFuncSeparate(int face, int func, int ref, int mask);
    void glStencilMaskSeparate(int face, int mask);
    void glShaderSource(int shader, LongBuffer strings, int [] length);
    void glUniform1fv(int location, float[] value);
    void glUniform2fv(int location, float[] value);
    void glUniform3fv(int location, float[] value);
    void glUniform4fv(int location, float[] value);
    void glUniform1iv(int location, int[] value);
    void glUniform2iv(int location, int[] value);
    void glUniform3iv(int location, int[] value);
    void glUniform4iv(int location, int[] value);
    void glUniformMatrix2fv(int location, boolean transpose, float[] value);
    void glUniformMatrix3fv(int location, boolean transpose, float[] value);
    void glUniformMatrix4fv(int location, boolean transpose, float[] value);
    void glGetShaderiv(int shader, int pname, int[] params);
    void glGetProgramiv(int program, int pname, int[] params);
    void glGetShaderInfoLog(int shader, int [] length, ByteBuffer infoLog);
    void glGetProgramInfoLog(int program, int [] length, ByteBuffer infoLog);
    void glGetAttachedShaders(int program, int [] count, int[] shaders);
    void glGetActiveUniform(int program, int index, int [] length, int[] size, int[] type, ByteBuffer name);
    void glGetUniformfv(int program, int location, float[] params);
    void glGetUniformiv(int program, int location, int[] params);
    void glGetShaderSource(int shader, int [] length, ByteBuffer source);
    void glVertexAttrib1fv(int index, float[] v);
    void glVertexAttrib1sv(int index, short[] v);
    void glVertexAttrib1dv(int index, double[] v);
    void glVertexAttrib2fv(int index, float[] v);
    void glVertexAttrib2sv(int index, short[] v);
    void glVertexAttrib2dv(int index, double[] v);
    void glVertexAttrib3fv(int index, float[] v);
    void glVertexAttrib3sv(int index, short[] v);
    void glVertexAttrib3dv(int index, double[] v);
    void glVertexAttrib4fv(int index, float[] v);
    void glVertexAttrib4sv(int index, short[] v);
    void glVertexAttrib4dv(int index, double[] v);
    void glVertexAttrib4iv(int index, int[] v);
    void glVertexAttrib4usv(int index, short[] v);
    void glVertexAttrib4uiv(int index, int[] v);
    void glVertexAttrib4Nsv(int index, short[] v);
    void glVertexAttrib4Niv(int index, int[] v);
    void glVertexAttrib4Nusv(int index, short[] v);
    void glVertexAttrib4Nuiv(int index, int[] v);
    void glGetActiveAttrib(int program, int index, int [] length, int[] size, int[] type, ByteBuffer name);
    void glGetVertexAttribiv(int index, int pname, int[] params);
    void glGetVertexAttribfv(int index, int pname, float[] params);
    void glGetVertexAttribdv(int index, int pname, double[] params);
    void glDrawBuffers(int[] bufs);

}
