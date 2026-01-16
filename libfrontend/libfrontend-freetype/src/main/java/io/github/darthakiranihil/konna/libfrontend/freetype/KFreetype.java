package io.github.darthakiranihil.konna.libfrontend.freetype;

import java.nio.*;

public interface KFreeType {

    int
        FT_ENCODING_NONE           = FT_ENC_TAG( 0, 0, 0, 0 ),
        FT_ENCODING_MS_SYMBOL      = FT_ENC_TAG( 's', 'y', 'm', 'b' ),
        FT_ENCODING_UNICODE        = FT_ENC_TAG( 'u', 'n', 'i', 'c' ),
        FT_ENCODING_SJIS           = FT_ENC_TAG( 's', 'j', 'i', 's' ),
        FT_ENCODING_PRC            = FT_ENC_TAG( 'g', 'b', ' ', ' ' ),
        FT_ENCODING_BIG5           = FT_ENC_TAG( 'b', 'i', 'g', '5' ),
        FT_ENCODING_WANSUNG        = FT_ENC_TAG( 'w', 'a', 'n', 's' ),
        FT_ENCODING_JOHAB          = FT_ENC_TAG( 'j', 'o', 'h', 'a' ),
        FT_ENCODING_GB2312         = FT_ENCODING_PRC,
        FT_ENCODING_MS_SJIS        = FT_ENCODING_SJIS,
        FT_ENCODING_MS_GB2312      = FT_ENCODING_PRC,
        FT_ENCODING_MS_BIG5        = FT_ENCODING_BIG5,
        FT_ENCODING_MS_WANSUNG     = FT_ENCODING_WANSUNG,
        FT_ENCODING_MS_JOHAB       = FT_ENCODING_JOHAB,
        FT_ENCODING_ADOBE_STANDARD = FT_ENC_TAG( 'A', 'D', 'O', 'B' ),
        FT_ENCODING_ADOBE_EXPERT   = FT_ENC_TAG( 'A', 'D', 'B', 'E' ),
        FT_ENCODING_ADOBE_CUSTOM   = FT_ENC_TAG( 'A', 'D', 'B', 'C' ),
        FT_ENCODING_ADOBE_LATIN_1  = FT_ENC_TAG( 'l', 'a', 't', '1' ),
        FT_ENCODING_OLD_LATIN_2    = FT_ENC_TAG( 'l', 'a', 't', '2' ),
        FT_ENCODING_APPLE_ROMAN    = FT_ENC_TAG( 'a', 'r', 'm', 'n' );

    int
        FT_FACE_FLAG_SCALABLE         = 1 <<  0,
        FT_FACE_FLAG_FIXED_SIZES      = 1 <<  1,
        FT_FACE_FLAG_FIXED_WIDTH      = 1 <<  2,
        FT_FACE_FLAG_SFNT             = 1 <<  3,
        FT_FACE_FLAG_HORIZONTAL       = 1 <<  4,
        FT_FACE_FLAG_VERTICAL         = 1 <<  5,
        FT_FACE_FLAG_KERNING          = 1 <<  6,
        FT_FACE_FLAG_FAST_GLYPHS      = 1 <<  7,
        FT_FACE_FLAG_MULTIPLE_MASTERS = 1 <<  8,
        FT_FACE_FLAG_GLYPH_NAMES      = 1 <<  9,
        FT_FACE_FLAG_EXTERNAL_STREAM  = 1 << 10,
        FT_FACE_FLAG_HINTER           = 1 << 11,
        FT_FACE_FLAG_CID_KEYED        = 1 << 12,
        FT_FACE_FLAG_TRICKY           = 1 << 13,
        FT_FACE_FLAG_COLOR            = 1 << 14,
        FT_FACE_FLAG_VARIATION        = 1 << 15,
        FT_FACE_FLAG_SVG              = 1 << 16,
        FT_FACE_FLAG_SBIX             = 1 << 17,
        FT_FACE_FLAG_SBIX_OVERLAY     = 1 << 18;

    int
        FT_STYLE_FLAG_ITALIC = 1 << 0,
        FT_STYLE_FLAG_BOLD   = 1 <<  1;

    int
        FT_OPEN_MEMORY   = 0x1,
        FT_OPEN_STREAM   = 0x2,
        FT_OPEN_PATHNAME = 0x4,
        FT_OPEN_DRIVER   = 0x8,
        FT_OPEN_PARAMS   = 0x10;

    int
        FT_SIZE_REQUEST_TYPE_NOMINAL  = 0,
        FT_SIZE_REQUEST_TYPE_REAL_DIM = 1,
        FT_SIZE_REQUEST_TYPE_BBOX     = 2,
        FT_SIZE_REQUEST_TYPE_CELL     = 3,
        FT_SIZE_REQUEST_TYPE_SCALES   = 4,
        FT_SIZE_REQUEST_TYPE_MAX      = 5;

    int
        FT_LOAD_DEFAULT                     = 0x0,
        FT_LOAD_NO_SCALE                    = 1 << 0,
        FT_LOAD_NO_HINTING                  = 1 << 1,
        FT_LOAD_RENDER                      = 1 << 2,
        FT_LOAD_NO_BITMAP                   = 1 << 3,
        FT_LOAD_VERTICAL_LAYOUT             = 1 << 4,
        FT_LOAD_FORCE_AUTOHINT              = 1 << 5,
        FT_LOAD_CROP_BITMAP                 = 1 << 6,
        FT_LOAD_PEDANTIC                    = 1 << 7,
        FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = 1 << 9,
        FT_LOAD_NO_RECURSE                  = 1 << 10,
        FT_LOAD_IGNORE_TRANSFORM            = 1 << 11,
        FT_LOAD_MONOCHROME                  = 1 << 12,
        FT_LOAD_LINEAR_DESIGN               = 1 << 13,
        FT_LOAD_SBITS_ONLY                  = 1 << 14,
        FT_LOAD_NO_AUTOHINT                 = 1 << 15,
        FT_LOAD_COLOR                       = 1 << 20,
        FT_LOAD_COMPUTE_METRICS             = 1 << 21,
        FT_LOAD_BITMAP_METRICS_ONLY         = 1 << 22,
        FT_LOAD_NO_SVG                      = 1 << 24,
        FT_LOAD_ADVANCE_ONLY                = 1 << 8,
        FT_LOAD_SVG_ONLY                    = 1 << 23;

    int
        FT_RENDER_MODE_NORMAL = 0,
        FT_RENDER_MODE_LIGHT  = 1,
        FT_RENDER_MODE_MONO   = 2,
        FT_RENDER_MODE_LCD    = 3,
        FT_RENDER_MODE_LCD_V  = 4,
        FT_RENDER_MODE_SDF    = 5,
        FT_RENDER_MODE_MAX    = 6;

    int
        FT_FT_LOAD_TARGET_NORMAL = FT_LOAD_TARGET_(FT_RENDER_MODE_NORMAL),
        FT_FT_LOAD_TARGET_LIGHT  = FT_LOAD_TARGET_(FT_RENDER_MODE_LIGHT),
        FT_FT_LOAD_TARGET_MONO   = FT_LOAD_TARGET_(FT_RENDER_MODE_MONO),
        FT_FT_LOAD_TARGET_LCD    = FT_LOAD_TARGET_(FT_RENDER_MODE_LCD),
        FT_FT_LOAD_TARGET_LCD_V  = FT_LOAD_TARGET_(FT_RENDER_MODE_LCD_V);

    int
        FT_KERNING_DEFAULT  = 0,
        FT_KERNING_UNFITTED = 1,
        FT_KERNING_UNSCALED = 2;

    int
        FT_SUBGLYPH_FLAG_ARGS_ARE_WORDS     = 1,
        FT_SUBGLYPH_FLAG_ARGS_ARE_XY_VALUES = 2,
        FT_SUBGLYPH_FLAG_ROUND_XY_TO_GRID   = 4,
        FT_SUBGLYPH_FLAG_SCALE              = 8,
        FT_SUBGLYPH_FLAG_XY_SCALE           = 0x40,
        FT_SUBGLYPH_FLAG_2X2                = 0x80,
        FT_SUBGLYPH_FLAG_USE_MY_METRICS     = 0x200;

    int
        FT_FSTYPE_INSTALLABLE_EMBEDDING        = 0x0000,
        FT_FSTYPE_RESTRICTED_LICENSE_EMBEDDING = 0x0002,
        FT_FSTYPE_PREVIEW_AND_PRINT_EMBEDDING  = 0x0004,
        FT_FSTYPE_EDITABLE_EMBEDDING           = 0x0008,
        FT_FSTYPE_NO_SUBSETTING                = 0x0100,
        FT_FSTYPE_BITMAP_EMBEDDING_ONLY        = 0x0200;

    int FREETYPE_MAJOR = 2;

    int FREETYPE_MINOR = 14;

    int FREETYPE_PATCH = 1;

    int FT_ADVANCE_FLAG_FAST_ONLY = 0x20000000;

    int
        BDF_PROPERTY_TYPE_NONE     = 0,
        BDF_PROPERTY_TYPE_ATOM     = 1,
        BDF_PROPERTY_TYPE_INTEGER  = 2,
        BDF_PROPERTY_TYPE_CARDINAL = 3;

    int
        FT_PALETTE_FOR_LIGHT_BACKGROUND = 0x01,
        FT_PALETTE_FOR_DARK_BACKGROUND  = 0x02;

    int
        FT_COLR_PAINTFORMAT_COLR_LAYERS     = 1,
        FT_COLR_PAINTFORMAT_SOLID           = 2,
        FT_COLR_PAINTFORMAT_LINEAR_GRADIENT = 4,
        FT_COLR_PAINTFORMAT_RADIAL_GRADIENT = 6,
        FT_COLR_PAINTFORMAT_SWEEP_GRADIENT  = 8,
        FT_COLR_PAINTFORMAT_GLYPH           = 10,
        FT_COLR_PAINTFORMAT_COLR_GLYPH      = 11,
        FT_COLR_PAINTFORMAT_TRANSFORM       = 12,
        FT_COLR_PAINTFORMAT_TRANSLATE       = 14,
        FT_COLR_PAINTFORMAT_SCALE           = 16,
        FT_COLR_PAINTFORMAT_ROTATE          = 24,
        FT_COLR_PAINTFORMAT_SKEW            = 28,
        FT_COLR_PAINTFORMAT_COMPOSITE       = 32,
        FT_COLR_PAINT_FORMAT_MAX            = 33,
        FT_COLR_PAINTFORMAT_UNSUPPORTED     = 255;

    int
        FT_COLR_PAINT_EXTEND_PAD     = 0,
        FT_COLR_PAINT_EXTEND_REPEAT  = 1,
        FT_COLR_PAINT_EXTEND_REFLECT = 2;

    int
        FT_COLR_COMPOSITE_CLEAR          = 0,
        FT_COLR_COMPOSITE_SRC            = 1,
        FT_COLR_COMPOSITE_DEST           = 2,
        FT_COLR_COMPOSITE_SRC_OVER       = 3,
        FT_COLR_COMPOSITE_DEST_OVER      = 4,
        FT_COLR_COMPOSITE_SRC_IN         = 5,
        FT_COLR_COMPOSITE_DEST_IN        = 6,
        FT_COLR_COMPOSITE_SRC_OUT        = 7,
        FT_COLR_COMPOSITE_DEST_OUT       = 8,
        FT_COLR_COMPOSITE_SRC_ATOP       = 9,
        FT_COLR_COMPOSITE_DEST_ATOP      = 10,
        FT_COLR_COMPOSITE_XOR            = 11,
        FT_COLR_COMPOSITE_PLUS           = 12,
        FT_COLR_COMPOSITE_SCREEN         = 13,
        FT_COLR_COMPOSITE_OVERLAY        = 14,
        FT_COLR_COMPOSITE_DARKEN         = 15,
        FT_COLR_COMPOSITE_LIGHTEN        = 16,
        FT_COLR_COMPOSITE_COLOR_DODGE    = 17,
        FT_COLR_COMPOSITE_COLOR_BURN     = 18,
        FT_COLR_COMPOSITE_HARD_LIGHT     = 19,
        FT_COLR_COMPOSITE_SOFT_LIGHT     = 20,
        FT_COLR_COMPOSITE_DIFFERENCE     = 21,
        FT_COLR_COMPOSITE_EXCLUSION      = 22,
        FT_COLR_COMPOSITE_MULTIPLY       = 23,
        FT_COLR_COMPOSITE_HSL_HUE        = 24,
        FT_COLR_COMPOSITE_HSL_SATURATION = 25,
        FT_COLR_COMPOSITE_HSL_COLOR      = 26,
        FT_COLR_COMPOSITE_HSL_LUMINOSITY = 27,
        FT_COLR_COMPOSITE_MAX            = 28;

    int
        FT_COLOR_INCLUDE_ROOT_TRANSFORM = 0,
        FT_COLOR_NO_ROOT_TRANSFORM      = 1,
        FT_COLOR_ROOT_TRANSFORM_MAX     = 2;

    int
        FT_HINTING_FREETYPE = 0,
        FT_HINTING_ADOBE    = 1;

    int TT_INTERPRETER_VERSION_35 = 35;

    int TT_INTERPRETER_VERSION_38 = 38;

    int TT_INTERPRETER_VERSION_40 = 40;

    int
        FT_AUTOHINTER_SCRIPT_NONE  = 0,
        FT_AUTOHINTER_SCRIPT_LATIN = 1,
        FT_AUTOHINTER_SCRIPT_CJK   = 2,
        FT_AUTOHINTER_SCRIPT_INDIC = 3;

    int
        FT_Err_Ok                            = 0x00,
        FT_Err_Cannot_Open_Resource          = 0x01,
        FT_Err_Unknown_File_Format           = 0x02,
        FT_Err_Invalid_File_Format           = 0x03,
        FT_Err_Invalid_Version               = 0x04,
        FT_Err_Lower_Module_Version          = 0x05,
        FT_Err_Invalid_Argument              = 0x06,
        FT_Err_Unimplemented_Feature         = 0x07,
        FT_Err_Invalid_Table                 = 0x08,
        FT_Err_Invalid_Offset                = 0x09,
        FT_Err_Array_Too_Large               = 0x0A,
        FT_Err_Missing_Module                = 0x0B,
        FT_Err_Missing_Property              = 0x0C,
        FT_Err_Invalid_Glyph_Index           = 0x10,
        FT_Err_Invalid_Character_Code        = 0x11,
        FT_Err_Invalid_Glyph_Format          = 0x12,
        FT_Err_Cannot_Render_Glyph           = 0x13,
        FT_Err_Invalid_Outline               = 0x14,
        FT_Err_Invalid_Composite             = 0x15,
        FT_Err_Too_Many_Hints                = 0x16,
        FT_Err_Invalid_Pixel_Size            = 0x17,
        FT_Err_Invalid_SVG_Document          = 0x18,
        FT_Err_Invalid_Handle                = 0x20,
        FT_Err_Invalid_Library_Handle        = 0x21,
        FT_Err_Invalid_Driver_Handle         = 0x22,
        FT_Err_Invalid_Face_Handle           = 0x23,
        FT_Err_Invalid_Size_Handle           = 0x24,
        FT_Err_Invalid_Slot_Handle           = 0x25,
        FT_Err_Invalid_CharMap_Handle        = 0x26,
        FT_Err_Invalid_Cache_Handle          = 0x27,
        FT_Err_Invalid_Stream_Handle         = 0x28,
        FT_Err_Too_Many_Drivers              = 0x30,
        FT_Err_Too_Many_Extensions           = 0x31,
        FT_Err_Out_Of_Memory                 = 0x40,
        FT_Err_Unlisted_Object               = 0x41,
        FT_Err_Cannot_Open_Stream            = 0x51,
        FT_Err_Invalid_Stream_Seek           = 0x52,
        FT_Err_Invalid_Stream_Skip           = 0x53,
        FT_Err_Invalid_Stream_Read           = 0x54,
        FT_Err_Invalid_Stream_Operation      = 0x55,
        FT_Err_Invalid_Frame_Operation       = 0x56,
        FT_Err_Nested_Frame_Access           = 0x57,
        FT_Err_Invalid_Frame_Read            = 0x58,
        FT_Err_Raster_Uninitialized          = 0x60,
        FT_Err_Raster_Corrupted              = 0x61,
        FT_Err_Raster_Overflow               = 0x62,
        FT_Err_Raster_Negative_Height        = 0x63,
        FT_Err_Too_Many_Caches               = 0x70,
        FT_Err_Invalid_Opcode                = 0x80,
        FT_Err_Too_Few_Arguments             = 0x81,
        FT_Err_Stack_Overflow                = 0x82,
        FT_Err_Code_Overflow                 = 0x83,
        FT_Err_Bad_Argument                  = 0x84,
        FT_Err_Divide_By_Zero                = 0x85,
        FT_Err_Invalid_Reference             = 0x86,
        FT_Err_Debug_OpCode                  = 0x87,
        FT_Err_ENDF_In_Exec_Stream           = 0x88,
        FT_Err_Nested_DEFS                   = 0x89,
        FT_Err_Invalid_CodeRange             = 0x8A,
        FT_Err_Execution_Too_Long            = 0x8B,
        FT_Err_Too_Many_Function_Defs        = 0x8C,
        FT_Err_Too_Many_Instruction_Defs     = 0x8D,
        FT_Err_Table_Missing                 = 0x8E,
        FT_Err_Horiz_Header_Missing          = 0x8F,
        FT_Err_Locations_Missing             = 0x90,
        FT_Err_Name_Table_Missing            = 0x91,
        FT_Err_CMap_Table_Missing            = 0x92,
        FT_Err_Hmtx_Table_Missing            = 0x93,
        FT_Err_Post_Table_Missing            = 0x94,
        FT_Err_Invalid_Horiz_Metrics         = 0x95,
        FT_Err_Invalid_CharMap_Format        = 0x96,
        FT_Err_Invalid_PPem                  = 0x97,
        FT_Err_Invalid_Vert_Metrics          = 0x98,
        FT_Err_Could_Not_Find_Context        = 0x99,
        FT_Err_Invalid_Post_Table_Format     = 0x9A,
        FT_Err_Invalid_Post_Table            = 0x9B,
        FT_Err_DEF_In_Glyf_Bytecode          = 0x9C,
        FT_Err_Missing_Bitmap                = 0x9D,
        FT_Err_Missing_SVG_Hooks             = 0x9E,
        FT_Err_Syntax_Error                  = 0xA0,
        FT_Err_Stack_Underflow               = 0xA1,
        FT_Err_Ignore                        = 0xA2,
        FT_Err_No_Unicode_Glyph_Name         = 0xA3,
        FT_Err_Glyph_Too_Big                 = 0xA4,
        FT_Err_Missing_Startfont_Field       = 0xB0,
        FT_Err_Missing_Font_Field            = 0xB1,
        FT_Err_Missing_Size_Field            = 0xB2,
        FT_Err_Missing_Fontboundingbox_Field = 0xB3,
        FT_Err_Missing_Chars_Field           = 0xB4,
        FT_Err_Missing_Startchar_Field       = 0xB5,
        FT_Err_Missing_Encoding_Field        = 0xB6,
        FT_Err_Missing_Bbx_Field             = 0xB7,
        FT_Err_Bbx_Too_Big                   = 0xB8,
        FT_Err_Corrupted_Font_Header         = 0xB9,
        FT_Err_Corrupted_Font_Glyphs         = 0xBA,
        FT_Err_Max                           = 187;

    int
        FT_GASP_NO_TABLE            = -1,
        FT_GASP_DO_GRIDFIT          = 0x01,
        FT_GASP_DO_GRAY             = 0x02,
        FT_GASP_SYMMETRIC_GRIDFIT   = 0x04,
        FT_GASP_SYMMETRIC_SMOOTHING = 0x08;

    int
        FT_GLYPH_BBOX_UNSCALED  = 0,
        FT_GLYPH_BBOX_SUBPIXELS = 0,
        FT_GLYPH_BBOX_GRIDFIT   = 1,
        FT_GLYPH_BBOX_TRUNCATE  = 2,
        FT_GLYPH_BBOX_PIXELS    = 3;

    int FT_VALIDATE_feat_INDEX = 0;

    int FT_VALIDATE_mort_INDEX = 1;

    int FT_VALIDATE_morx_INDEX = 2;

    int FT_VALIDATE_bsln_INDEX = 3;

    int FT_VALIDATE_just_INDEX = 4;

    int FT_VALIDATE_kern_INDEX = 5;

    int FT_VALIDATE_opbd_INDEX = 6;

    int FT_VALIDATE_trak_INDEX = 7;

    int FT_VALIDATE_prop_INDEX = 8;

    int FT_VALIDATE_lcar_INDEX = 9;

    int FT_VALIDATE_GX_LAST_INDEX = FT_VALIDATE_lcar_INDEX;

    int FT_VALIDATE_GX_LENGTH = FT_VALIDATE_GX_LAST_INDEX + 1;

    int FT_VALIDATE_GX_START = 0x4000;

    int FT_VALIDATE_feat = FT_VALIDATE_GX_START << 0;

    int FT_VALIDATE_mort = FT_VALIDATE_GX_START << 1;

    int FT_VALIDATE_morx = FT_VALIDATE_GX_START << 2;

    int FT_VALIDATE_bsln = FT_VALIDATE_GX_START << 3;

    int FT_VALIDATE_just = FT_VALIDATE_GX_START << 4;

    int FT_VALIDATE_kern = FT_VALIDATE_GX_START << 5;

    int FT_VALIDATE_opbd = FT_VALIDATE_GX_START << 6;

    int FT_VALIDATE_trak = FT_VALIDATE_GX_START << 7;

    int FT_VALIDATE_prop = FT_VALIDATE_GX_START << 8;

    int FT_VALIDATE_lcar = FT_VALIDATE_GX_START << 9;

    int FT_FT_VALIDATE_GX =
        FT_VALIDATE_feat |
            FT_VALIDATE_mort |
            FT_VALIDATE_morx |
            FT_VALIDATE_bsln |
            FT_VALIDATE_just |
            FT_VALIDATE_kern |
            FT_VALIDATE_opbd |
            FT_VALIDATE_trak |
            FT_VALIDATE_prop |
            FT_VALIDATE_lcar;

    int
        FT_VALIDATE_MS    = FT_VALIDATE_GX_START << 0,
        FT_VALIDATE_APPLE = FT_VALIDATE_GX_START << 1,
        FT_VALIDATE_CKERN = FT_VALIDATE_MS | FT_VALIDATE_APPLE;

    int
        FT_PIXEL_MODE_NONE  = 0,
        FT_PIXEL_MODE_MONO  = 1,
        FT_PIXEL_MODE_GRAY  = 2,
        FT_PIXEL_MODE_GRAY2 = 3,
        FT_PIXEL_MODE_GRAY4 = 4,
        FT_PIXEL_MODE_LCD   = 5,
        FT_PIXEL_MODE_LCD_V = 6,
        FT_PIXEL_MODE_BGRA  = 7,
        FT_PIXEL_MODE_MAX   = 8;

    int
        FT_OUTLINE_CONTOURS_MAX = 0xFFFF,
        FT_OUTLINE_POINTS_MAX   = 0xFFFF;

    int
        FT_OUTLINE_NONE            = 0x0,
        FT_OUTLINE_OWNER           = 0x1,
        FT_OUTLINE_EVEN_ODD_FILL   = 0x2,
        FT_OUTLINE_REVERSE_FILL    = 0x4,
        FT_OUTLINE_IGNORE_DROPOUTS = 0x8,
        FT_OUTLINE_SMART_DROPOUTS  = 0x10,
        FT_OUTLINE_INCLUDE_STUBS   = 0x20,
        FT_OUTLINE_OVERLAP         = 0x40,
        FT_OUTLINE_HIGH_PRECISION  = 0x100,
        FT_OUTLINE_SINGLE_PASS     = 0x200;

    int
        FT_CURVE_TAG_ON           = 0x01,
        FT_CURVE_TAG_CONIC        = 0x00,
        FT_CURVE_TAG_CUBIC        = 0x02,
        FT_CURVE_TAG_HAS_SCANMODE = 0x04,
        FT_CURVE_TAG_TOUCH_X      = 0x08,
        FT_CURVE_TAG_TOUCH_Y      = 0x10,
        FT_CURVE_TAG_TOUCH_BOTH   = FT_CURVE_TAG_TOUCH_X | FT_CURVE_TAG_TOUCH_Y;

    int
        FT_GLYPH_FORMAT_NONE      = FT_IMAGE_TAG( 0, 0, 0, 0 ),
        FT_GLYPH_FORMAT_COMPOSITE = FT_IMAGE_TAG( 'c', 'o', 'm', 'p' ),
        FT_GLYPH_FORMAT_BITMAP    = FT_IMAGE_TAG( 'b', 'i', 't', 's' ),
        FT_GLYPH_FORMAT_OUTLINE   = FT_IMAGE_TAG( 'o', 'u', 't', 'l' ),
        FT_GLYPH_FORMAT_PLOTTER   = FT_IMAGE_TAG( 'p', 'l', 'o', 't' ),
        FT_GLYPH_FORMAT_SVG       = FT_IMAGE_TAG( 'S', 'V', 'G', ' ' );

    int
        FT_RASTER_FLAG_DEFAULT = 0x0,
        FT_RASTER_FLAG_AA      = 0x1,
        FT_RASTER_FLAG_DIRECT  = 0x2,
        FT_RASTER_FLAG_CLIP    = 0x4,
        FT_RASTER_FLAG_SDF     = 0x8;

    int
        FT_LCD_FILTER_NONE    = 0,
        FT_LCD_FILTER_DEFAULT = 1,
        FT_LCD_FILTER_LIGHT   = 2,
        FT_LCD_FILTER_LEGACY1 = 3,
        FT_LCD_FILTER_LEGACY  = 16,
        FT_LCD_FILTER_MAX     = 17;

    int FT_LCD_FILTER_FIVE_TAPS = 5;

    int T1_MAX_MM_AXIS = 4;

    int T1_MAX_MM_DESIGNS = 16;

    int T1_MAX_MM_MAP_POINTS = 20;

    int FT_VAR_AXIS_FLAG_HIDDEN = 1;

    int FT_MODULE_FONT_DRIVER = 1;

    int FT_MODULE_RENDERER = 2;

    int FT_MODULE_HINTER = 4;

    int FT_MODULE_STYLER = 8;

    int FT_MODULE_DRIVER_SCALABLE = 0x100;

    int FT_MODULE_DRIVER_NO_OUTLINES = 0x200;

    int FT_MODULE_DRIVER_HAS_HINTER = 0x400;

    int FT_MODULE_DRIVER_HINTS_LIGHTLY = 0x800;

    int FT_DEBUG_HOOK_TRUETYPE = 0;

    int
        FT_TRUETYPE_ENGINE_TYPE_NONE       = 0,
        FT_TRUETYPE_ENGINE_TYPE_UNPATENTED = 1,
        FT_TRUETYPE_ENGINE_TYPE_PATENTED   = 2;

    int
        FT_Mod_Err_Base     = 0x00,
        FT_Mod_Err_Autofit  = 0x100,
        FT_Mod_Err_BDF      = 0x200,
        FT_Mod_Err_Bzip2    = 0x300,
        FT_Mod_Err_Cache    = 0x400,
        FT_Mod_Err_CFF      = 0x500,
        FT_Mod_Err_CID      = 0x600,
        FT_Mod_Err_Gzip     = 0x700,
        FT_Mod_Err_LZW      = 0x800,
        FT_Mod_Err_OTvalid  = 0x900,
        FT_Mod_Err_PCF      = 0xA00,
        FT_Mod_Err_PFR      = 0xB00,
        FT_Mod_Err_PSaux    = 0xC00,
        FT_Mod_Err_PShinter = 0xD00,
        FT_Mod_Err_PSnames  = 0xE00,
        FT_Mod_Err_Raster   = 0xF00,
        FT_Mod_Err_SFNT     = 0x1000,
        FT_Mod_Err_Smooth   = 0x1100,
        FT_Mod_Err_TrueType = 0x1200,
        FT_Mod_Err_Type1    = 0x1300,
        FT_Mod_Err_Type42   = 0x1400,
        FT_Mod_Err_Winfonts = 0x1500,
        FT_Mod_Err_GXvalid  = 0x1600,
        FT_Mod_Err_Sdf      = 0x1700,
        FT_Mod_Err_Max      = FT_Mod_Err_Sdf + 1;

    int
        FT_VALIDATE_BASE = 0x0100,
        FT_VALIDATE_GDEF = 0x0200,
        FT_VALIDATE_GPOS = 0x0400,
        FT_VALIDATE_GSUB = 0x0800,
        FT_VALIDATE_JSTF = 0x1000,
        FT_VALIDATE_MATH = 0x2000,
        FT_VALIDATE_OT   =
            FT_VALIDATE_BASE |
                FT_VALIDATE_GDEF |
                FT_VALIDATE_GPOS |
                FT_VALIDATE_GSUB |
                FT_VALIDATE_JSTF |
                FT_VALIDATE_MATH;

    int
        FT_ORIENTATION_TRUETYPE   = 0,
        FT_ORIENTATION_POSTSCRIPT = 1,
        FT_ORIENTATION_FILL_RIGHT = FT_ORIENTATION_TRUETYPE,
        FT_ORIENTATION_FILL_LEFT  = FT_ORIENTATION_POSTSCRIPT,
        FT_ORIENTATION_NONE       = 2;

    int
        FT_PARAM_TAG_IGNORE_TYPOGRAPHIC_FAMILY    = FT_MAKE_TAG( 'i', 'g', 'p', 'f' ),
        FT_PARAM_TAG_IGNORE_TYPOGRAPHIC_SUBFAMILY = FT_MAKE_TAG( 'i', 'g', 'p', 's' ),
        FT_PARAM_TAG_INCREMENTAL                  = FT_MAKE_TAG( 'i', 'n', 'c', 'r' ),
        FT_PARAM_TAG_IGNORE_SBIX                  = FT_MAKE_TAG( 'i', 's', 'b', 'x' ),
        FT_PARAM_TAG_LCD_FILTER_WEIGHTS           = FT_MAKE_TAG( 'l', 'c', 'd', 'f' ),
        FT_PARAM_TAG_RANDOM_SEED                  = FT_MAKE_TAG( 's', 'e', 'e', 'd' ),
        FT_PARAM_TAG_STEM_DARKENING               = FT_MAKE_TAG( 'd', 'a', 'r', 'k' ),
        FT_PARAM_TAG_UNPATENTED_HINTING           = FT_MAKE_TAG( 'u', 'n', 'p', 'a' );

    int
        FT_STROKER_LINEJOIN_ROUND          = 0,
        FT_STROKER_LINEJOIN_BEVEL          = 1,
        FT_STROKER_LINEJOIN_MITER_VARIABLE = 2,
        FT_STROKER_LINEJOIN_MITER          = FT_STROKER_LINEJOIN_MITER_VARIABLE,
        FT_STROKER_LINEJOIN_MITER_FIXED    = 3;

    int
        FT_STROKER_LINECAP_BUTT   = 0,
        FT_STROKER_LINECAP_ROUND  = 1,
        FT_STROKER_LINECAP_SQUARE = 2;

    int
        FT_STROKER_BORDER_LEFT  = 0,
        FT_STROKER_BORDER_RIGHT = 1;

    int
        FT_ANGLE_PI  = 180 << 16,
        FT_ANGLE_2PI = FT_ANGLE_PI * 2,
        FT_ANGLE_PI2 = FT_ANGLE_PI / 2,
        FT_ANGLE_PI4 = FT_ANGLE_PI / 4;

    int
        T1_BLEND_UNDERLINE_POSITION  = 0,
        T1_BLEND_UNDERLINE_THICKNESS = 1,
        T1_BLEND_ITALIC_ANGLE        = 2,
        T1_BLEND_BLUE_VALUES         = 3,
        T1_BLEND_OTHER_BLUES         = 4,
        T1_BLEND_STANDARD_WIDTH      = 5,
        T1_BLEND_STANDARD_HEIGHT     = 6,
        T1_BLEND_STEM_SNAP_WIDTHS    = 7,
        T1_BLEND_STEM_SNAP_HEIGHTS   = 8,
        T1_BLEND_BLUE_SCALE          = 9,
        T1_BLEND_BLUE_SHIFT          = 10,
        T1_BLEND_FAMILY_BLUES        = 11,
        T1_BLEND_FAMILY_OTHER_BLUES  = 12,
        T1_BLEND_FORCE_BOLD          = 13,
        T1_BLEND_MAX                 = 14;

    int
        T1_ENCODING_TYPE_NONE      = 0,
        T1_ENCODING_TYPE_ARRAY     = 1,
        T1_ENCODING_TYPE_STANDARD  = 2,
        T1_ENCODING_TYPE_ISOLATIN1 = 3,
        T1_ENCODING_TYPE_EXPERT    = 4;

    int
        PS_DICT_FONT_TYPE              = 0,
        PS_DICT_FONT_MATRIX            = 1,
        PS_DICT_FONT_BBOX              = 2,
        PS_DICT_PAINT_TYPE             = 3,
        PS_DICT_FONT_NAME              = 4,
        PS_DICT_UNIQUE_ID              = 5,
        PS_DICT_NUM_CHAR_STRINGS       = 6,
        PS_DICT_CHAR_STRING_KEY        = 7,
        PS_DICT_CHAR_STRING            = 8,
        PS_DICT_ENCODING_TYPE          = 9,
        PS_DICT_ENCODING_ENTRY         = 10,
        PS_DICT_NUM_SUBRS              = 11,
        PS_DICT_SUBR                   = 12,
        PS_DICT_STD_HW                 = 13,
        PS_DICT_STD_VW                 = 14,
        PS_DICT_NUM_BLUE_VALUES        = 15,
        PS_DICT_BLUE_VALUE             = 16,
        PS_DICT_BLUE_FUZZ              = 17,
        PS_DICT_NUM_OTHER_BLUES        = 18,
        PS_DICT_OTHER_BLUE             = 19,
        PS_DICT_NUM_FAMILY_BLUES       = 20,
        PS_DICT_FAMILY_BLUE            = 21,
        PS_DICT_NUM_FAMILY_OTHER_BLUES = 22,
        PS_DICT_FAMILY_OTHER_BLUE      = 23,
        PS_DICT_BLUE_SCALE             = 24,
        PS_DICT_BLUE_SHIFT             = 25,
        PS_DICT_NUM_STEM_SNAP_H        = 26,
        PS_DICT_STEM_SNAP_H            = 27,
        PS_DICT_NUM_STEM_SNAP_V        = 28,
        PS_DICT_STEM_SNAP_V            = 29,
        PS_DICT_FORCE_BOLD             = 30,
        PS_DICT_RND_STEM_UP            = 31,
        PS_DICT_MIN_FEATURE            = 32,
        PS_DICT_LEN_IV                 = 33,
        PS_DICT_PASSWORD               = 34,
        PS_DICT_LANGUAGE_GROUP         = 35,
        PS_DICT_VERSION                = 36,
        PS_DICT_NOTICE                 = 37,
        PS_DICT_FULL_NAME              = 38,
        PS_DICT_FAMILY_NAME            = 39,
        PS_DICT_WEIGHT                 = 40,
        PS_DICT_IS_FIXED_PITCH         = 41,
        PS_DICT_UNDERLINE_POSITION     = 42,
        PS_DICT_UNDERLINE_THICKNESS    = 43,
        PS_DICT_FS_TYPE                = 44,
        PS_DICT_ITALIC_ANGLE           = 45,
        PS_DICT_MAX                    = PS_DICT_ITALIC_ANGLE;

    int
        TT_PLATFORM_APPLE_UNICODE = 0,
        TT_PLATFORM_MACINTOSH     = 1,
        TT_PLATFORM_ISO           = 2,
        TT_PLATFORM_MICROSOFT     = 3,
        TT_PLATFORM_CUSTOM        = 4,
        TT_PLATFORM_ADOBE         = 7;

    int
        TT_APPLE_ID_DEFAULT          = 0,
        TT_APPLE_ID_UNICODE_1_1      = 1,
        TT_APPLE_ID_ISO_10646        = 2,
        TT_APPLE_ID_UNICODE_2_0      = 3,
        TT_APPLE_ID_UNICODE_32       = 4,
        TT_APPLE_ID_VARIANT_SELECTOR = 5,
        TT_APPLE_ID_FULL_UNICODE     = 6;

    int
        TT_MAC_ID_ROMAN               = 0,
        TT_MAC_ID_JAPANESE            = 1,
        TT_MAC_ID_TRADITIONAL_CHINESE = 2,
        TT_MAC_ID_KOREAN              = 3,
        TT_MAC_ID_ARABIC              = 4,
        TT_MAC_ID_HEBREW              = 5,
        TT_MAC_ID_GREEK               = 6,
        TT_MAC_ID_RUSSIAN             = 7,
        TT_MAC_ID_RSYMBOL             = 8,
        TT_MAC_ID_DEVANAGARI          = 9,
        TT_MAC_ID_GURMUKHI            = 10,
        TT_MAC_ID_GUJARATI            = 11,
        TT_MAC_ID_ORIYA               = 12,
        TT_MAC_ID_BENGALI             = 13,
        TT_MAC_ID_TAMIL               = 14,
        TT_MAC_ID_TELUGU              = 15,
        TT_MAC_ID_KANNADA             = 16,
        TT_MAC_ID_MALAYALAM           = 17,
        TT_MAC_ID_SINHALESE           = 18,
        TT_MAC_ID_BURMESE             = 19,
        TT_MAC_ID_KHMER               = 20,
        TT_MAC_ID_THAI                = 21,
        TT_MAC_ID_LAOTIAN             = 22,
        TT_MAC_ID_GEORGIAN            = 23,
        TT_MAC_ID_ARMENIAN            = 24,
        TT_MAC_ID_MALDIVIAN           = 25,
        TT_MAC_ID_SIMPLIFIED_CHINESE  = 26,
        TT_MAC_ID_TIBETAN             = 27,
        TT_MAC_ID_MONGOLIAN           = 28,
        TT_MAC_ID_GEEZ                = 29,
        TT_MAC_ID_SLAVIC              = 30,
        TT_MAC_ID_VIETNAMESE          = 31,
        TT_MAC_ID_SINDHI              = 32,
        TT_MAC_ID_UNINTERP            = 33;

    int
        TT_ISO_ID_7BIT_ASCII = 0,
        TT_ISO_ID_10646      = 1,
        TT_ISO_ID_8859_1     = 2;

    int
        TT_MS_ID_SYMBOL_CS  = 0,
        TT_MS_ID_UNICODE_CS = 1,
        TT_MS_ID_SJIS       = 2,
        TT_MS_ID_PRC        = 3,
        TT_MS_ID_BIG_5      = 4,
        TT_MS_ID_WANSUNG    = 5,
        TT_MS_ID_JOHAB      = 6,
        TT_MS_ID_UCS_4      = 10;

    int
        TT_ADOBE_ID_STANDARD = 0,
        TT_ADOBE_ID_EXPERT   = 1,
        TT_ADOBE_ID_CUSTOM   = 2,
        TT_ADOBE_ID_LATIN_1  = 3;

    int
        TT_MAC_LANGID_ENGLISH                     = 0,
        TT_MAC_LANGID_FRENCH                      = 1,
        TT_MAC_LANGID_GERMAN                      = 2,
        TT_MAC_LANGID_ITALIAN                     = 3,
        TT_MAC_LANGID_DUTCH                       = 4,
        TT_MAC_LANGID_SWEDISH                     = 5,
        TT_MAC_LANGID_SPANISH                     = 6,
        TT_MAC_LANGID_DANISH                      = 7,
        TT_MAC_LANGID_PORTUGUESE                  = 8,
        TT_MAC_LANGID_NORWEGIAN                   = 9,
        TT_MAC_LANGID_HEBREW                      = 10,
        TT_MAC_LANGID_JAPANESE                    = 11,
        TT_MAC_LANGID_ARABIC                      = 12,
        TT_MAC_LANGID_FINNISH                     = 13,
        TT_MAC_LANGID_GREEK                       = 14,
        TT_MAC_LANGID_ICELANDIC                   = 15,
        TT_MAC_LANGID_MALTESE                     = 16,
        TT_MAC_LANGID_TURKISH                     = 17,
        TT_MAC_LANGID_CROATIAN                    = 18,
        TT_MAC_LANGID_CHINESE_TRADITIONAL         = 19,
        TT_MAC_LANGID_URDU                        = 20,
        TT_MAC_LANGID_HINDI                       = 21,
        TT_MAC_LANGID_THAI                        = 22,
        TT_MAC_LANGID_KOREAN                      = 23,
        TT_MAC_LANGID_LITHUANIAN                  = 24,
        TT_MAC_LANGID_POLISH                      = 25,
        TT_MAC_LANGID_HUNGARIAN                   = 26,
        TT_MAC_LANGID_ESTONIAN                    = 27,
        TT_MAC_LANGID_LETTISH                     = 28,
        TT_MAC_LANGID_SAAMISK                     = 29,
        TT_MAC_LANGID_FAEROESE                    = 30,
        TT_MAC_LANGID_FARSI                       = 31,
        TT_MAC_LANGID_RUSSIAN                     = 32,
        TT_MAC_LANGID_CHINESE_SIMPLIFIED          = 33,
        TT_MAC_LANGID_FLEMISH                     = 34,
        TT_MAC_LANGID_IRISH                       = 35,
        TT_MAC_LANGID_ALBANIAN                    = 36,
        TT_MAC_LANGID_ROMANIAN                    = 37,
        TT_MAC_LANGID_CZECH                       = 38,
        TT_MAC_LANGID_SLOVAK                      = 39,
        TT_MAC_LANGID_SLOVENIAN                   = 40,
        TT_MAC_LANGID_YIDDISH                     = 41,
        TT_MAC_LANGID_SERBIAN                     = 42,
        TT_MAC_LANGID_MACEDONIAN                  = 43,
        TT_MAC_LANGID_BULGARIAN                   = 44,
        TT_MAC_LANGID_UKRAINIAN                   = 45,
        TT_MAC_LANGID_BYELORUSSIAN                = 46,
        TT_MAC_LANGID_UZBEK                       = 47,
        TT_MAC_LANGID_KAZAKH                      = 48,
        TT_MAC_LANGID_AZERBAIJANI                 = 49,
        TT_MAC_LANGID_AZERBAIJANI_CYRILLIC_SCRIPT = 50,
        TT_MAC_LANGID_AZERBAIJANI_ARABIC_SCRIPT   = 51,
        TT_MAC_LANGID_ARMENIAN                    = 52,
        TT_MAC_LANGID_GEORGIAN                    = 53,
        TT_MAC_LANGID_MOLDAVIAN                   = 54,
        TT_MAC_LANGID_KIRGHIZ                     = 55,
        TT_MAC_LANGID_TAJIKI                      = 56,
        TT_MAC_LANGID_TURKMEN                     = 57,
        TT_MAC_LANGID_MONGOLIAN                   = 58,
        TT_MAC_LANGID_MONGOLIAN_MONGOLIAN_SCRIPT  = 59,
        TT_MAC_LANGID_MONGOLIAN_CYRILLIC_SCRIPT   = 60,
        TT_MAC_LANGID_PASHTO                      = 61,
        TT_MAC_LANGID_KURDISH                     = 62,
        TT_MAC_LANGID_KASHMIRI                    = 63,
        TT_MAC_LANGID_SINDHI                      = 64,
        TT_MAC_LANGID_TIBETAN                     = 65,
        TT_MAC_LANGID_NEPALI                      = 66,
        TT_MAC_LANGID_SANSKRIT                    = 67,
        TT_MAC_LANGID_MARATHI                     = 68,
        TT_MAC_LANGID_BENGALI                     = 69,
        TT_MAC_LANGID_ASSAMESE                    = 70,
        TT_MAC_LANGID_GUJARATI                    = 71,
        TT_MAC_LANGID_PUNJABI                     = 72,
        TT_MAC_LANGID_ORIYA                       = 73,
        TT_MAC_LANGID_MALAYALAM                   = 74,
        TT_MAC_LANGID_KANNADA                     = 75,
        TT_MAC_LANGID_TAMIL                       = 76,
        TT_MAC_LANGID_TELUGU                      = 77,
        TT_MAC_LANGID_SINHALESE                   = 78,
        TT_MAC_LANGID_BURMESE                     = 79,
        TT_MAC_LANGID_KHMER                       = 80,
        TT_MAC_LANGID_LAO                         = 81,
        TT_MAC_LANGID_VIETNAMESE                  = 82,
        TT_MAC_LANGID_INDONESIAN                  = 83,
        TT_MAC_LANGID_TAGALOG                     = 84,
        TT_MAC_LANGID_MALAY_ROMAN_SCRIPT          = 85,
        TT_MAC_LANGID_MALAY_ARABIC_SCRIPT         = 86,
        TT_MAC_LANGID_AMHARIC                     = 87,
        TT_MAC_LANGID_TIGRINYA                    = 88,
        TT_MAC_LANGID_GALLA                       = 89,
        TT_MAC_LANGID_SOMALI                      = 90,
        TT_MAC_LANGID_SWAHILI                     = 91,
        TT_MAC_LANGID_RUANDA                      = 92,
        TT_MAC_LANGID_RUNDI                       = 93,
        TT_MAC_LANGID_CHEWA                       = 94,
        TT_MAC_LANGID_MALAGASY                    = 95,
        TT_MAC_LANGID_ESPERANTO                   = 96,
        TT_MAC_LANGID_WELSH                       = 128,
        TT_MAC_LANGID_BASQUE                      = 129,
        TT_MAC_LANGID_CATALAN                     = 130,
        TT_MAC_LANGID_LATIN                       = 131,
        TT_MAC_LANGID_QUECHUA                     = 132,
        TT_MAC_LANGID_GUARANI                     = 133,
        TT_MAC_LANGID_AYMARA                      = 134,
        TT_MAC_LANGID_TATAR                       = 135,
        TT_MAC_LANGID_UIGHUR                      = 136,
        TT_MAC_LANGID_DZONGKHA                    = 137,
        TT_MAC_LANGID_JAVANESE                    = 138,
        TT_MAC_LANGID_SUNDANESE                   = 139,
        TT_MAC_LANGID_GALICIAN                    = 140,
        TT_MAC_LANGID_AFRIKAANS                   = 141,
        TT_MAC_LANGID_BRETON                      = 142,
        TT_MAC_LANGID_INUKTITUT                   = 143,
        TT_MAC_LANGID_SCOTTISH_GAELIC             = 144,
        TT_MAC_LANGID_MANX_GAELIC                 = 145,
        TT_MAC_LANGID_IRISH_GAELIC                = 146,
        TT_MAC_LANGID_TONGAN                      = 147,
        TT_MAC_LANGID_GREEK_POLYTONIC             = 148,
        TT_MAC_LANGID_GREELANDIC                  = 149,
        TT_MAC_LANGID_AZERBAIJANI_ROMAN_SCRIPT    = 150;

    int
        TT_MS_LANGID_ARABIC_SAUDI_ARABIA            = 0x0401,
        TT_MS_LANGID_ARABIC_IRAQ                    = 0x0801,
        TT_MS_LANGID_ARABIC_EGYPT                   = 0x0C01,
        TT_MS_LANGID_ARABIC_LIBYA                   = 0x1001,
        TT_MS_LANGID_ARABIC_ALGERIA                 = 0x1401,
        TT_MS_LANGID_ARABIC_MOROCCO                 = 0x1801,
        TT_MS_LANGID_ARABIC_TUNISIA                 = 0x1C01,
        TT_MS_LANGID_ARABIC_OMAN                    = 0x2001,
        TT_MS_LANGID_ARABIC_YEMEN                   = 0x2401,
        TT_MS_LANGID_ARABIC_SYRIA                   = 0x2801,
        TT_MS_LANGID_ARABIC_JORDAN                  = 0x2C01,
        TT_MS_LANGID_ARABIC_LEBANON                 = 0x3001,
        TT_MS_LANGID_ARABIC_KUWAIT                  = 0x3401,
        TT_MS_LANGID_ARABIC_UAE                     = 0x3801,
        TT_MS_LANGID_ARABIC_BAHRAIN                 = 0x3C01,
        TT_MS_LANGID_ARABIC_QATAR                   = 0x4001,
        TT_MS_LANGID_BULGARIAN_BULGARIA             = 0x0402,
        TT_MS_LANGID_CATALAN_CATALAN                = 0x0403,
        TT_MS_LANGID_CHINESE_TAIWAN                 = 0x0404,
        TT_MS_LANGID_CHINESE_PRC                    = 0x0804,
        TT_MS_LANGID_CHINESE_HONG_KONG              = 0x0C04,
        TT_MS_LANGID_CHINESE_SINGAPORE              = 0x1004,
        TT_MS_LANGID_CHINESE_MACAO                  = 0x1404,
        TT_MS_LANGID_CZECH_CZECH_REPUBLIC           = 0x0405,
        TT_MS_LANGID_DANISH_DENMARK                 = 0x0406,
        TT_MS_LANGID_GERMAN_GERMANY                 = 0x0407,
        TT_MS_LANGID_GERMAN_SWITZERLAND             = 0x0807,
        TT_MS_LANGID_GERMAN_AUSTRIA                 = 0x0C07,
        TT_MS_LANGID_GERMAN_LUXEMBOURG              = 0x1007,
        TT_MS_LANGID_GERMAN_LIECHTENSTEIN           = 0x1407,
        TT_MS_LANGID_GREEK_GREECE                   = 0x0408,
        TT_MS_LANGID_ENGLISH_UNITED_STATES          = 0x0409,
        TT_MS_LANGID_ENGLISH_UNITED_KINGDOM         = 0x0809,
        TT_MS_LANGID_ENGLISH_AUSTRALIA              = 0x0C09,
        TT_MS_LANGID_ENGLISH_CANADA                 = 0x1009,
        TT_MS_LANGID_ENGLISH_NEW_ZEALAND            = 0x1409,
        TT_MS_LANGID_ENGLISH_IRELAND                = 0x1809,
        TT_MS_LANGID_ENGLISH_SOUTH_AFRICA           = 0x1C09,
        TT_MS_LANGID_ENGLISH_JAMAICA                = 0x2009,
        TT_MS_LANGID_ENGLISH_CARIBBEAN              = 0x2409,
        TT_MS_LANGID_ENGLISH_BELIZE                 = 0x2809,
        TT_MS_LANGID_ENGLISH_TRINIDAD               = 0x2C09,
        TT_MS_LANGID_ENGLISH_ZIMBABWE               = 0x3009,
        TT_MS_LANGID_ENGLISH_PHILIPPINES            = 0x3409,
        TT_MS_LANGID_ENGLISH_INDIA                  = 0x4009,
        TT_MS_LANGID_ENGLISH_MALAYSIA               = 0x4409,
        TT_MS_LANGID_ENGLISH_SINGAPORE              = 0x4809,
        TT_MS_LANGID_SPANISH_SPAIN_TRADITIONAL_SORT = 0x040A,
        TT_MS_LANGID_SPANISH_MEXICO                 = 0x080A,
        TT_MS_LANGID_SPANISH_SPAIN_MODERN_SORT      = 0x0C0A,
        TT_MS_LANGID_SPANISH_GUATEMALA              = 0x100A,
        TT_MS_LANGID_SPANISH_COSTA_RICA             = 0x140A,
        TT_MS_LANGID_SPANISH_PANAMA                 = 0x180A,
        TT_MS_LANGID_SPANISH_DOMINICAN_REPUBLIC     = 0x1C0A,
        TT_MS_LANGID_SPANISH_VENEZUELA              = 0x200A,
        TT_MS_LANGID_SPANISH_COLOMBIA               = 0x240A,
        TT_MS_LANGID_SPANISH_PERU                   = 0x280A,
        TT_MS_LANGID_SPANISH_ARGENTINA              = 0x2C0A,
        TT_MS_LANGID_SPANISH_ECUADOR                = 0x300A,
        TT_MS_LANGID_SPANISH_CHILE                  = 0x340A,
        TT_MS_LANGID_SPANISH_URUGUAY                = 0x380A,
        TT_MS_LANGID_SPANISH_PARAGUAY               = 0x3C0A,
        TT_MS_LANGID_SPANISH_BOLIVIA                = 0x400A,
        TT_MS_LANGID_SPANISH_EL_SALVADOR            = 0x440A,
        TT_MS_LANGID_SPANISH_HONDURAS               = 0x480A,
        TT_MS_LANGID_SPANISH_NICARAGUA              = 0x4C0A,
        TT_MS_LANGID_SPANISH_PUERTO_RICO            = 0x500A,
        TT_MS_LANGID_SPANISH_UNITED_STATES          = 0x540A,
        TT_MS_LANGID_FINNISH_FINLAND                = 0x040B,
        TT_MS_LANGID_FRENCH_FRANCE                  = 0x040C,
        TT_MS_LANGID_FRENCH_BELGIUM                 = 0x080C,
        TT_MS_LANGID_FRENCH_CANADA                  = 0x0C0C,
        TT_MS_LANGID_FRENCH_SWITZERLAND             = 0x100C,
        TT_MS_LANGID_FRENCH_LUXEMBOURG              = 0x140C,
        TT_MS_LANGID_FRENCH_MONACO                  = 0x180C,
        TT_MS_LANGID_HEBREW_ISRAEL                  = 0x040D,
        TT_MS_LANGID_HUNGARIAN_HUNGARY              = 0x040E,
        TT_MS_LANGID_ICELANDIC_ICELAND              = 0x040F,
        TT_MS_LANGID_ITALIAN_ITALY                  = 0x0410,
        TT_MS_LANGID_ITALIAN_SWITZERLAND            = 0x0810,
        TT_MS_LANGID_JAPANESE_JAPAN                 = 0x0411,
        TT_MS_LANGID_KOREAN_KOREA                   = 0x0412,
        TT_MS_LANGID_DUTCH_NETHERLANDS              = 0x0413,
        TT_MS_LANGID_DUTCH_BELGIUM                  = 0x0813,
        TT_MS_LANGID_NORWEGIAN_NORWAY_BOKMAL        = 0x0414,
        TT_MS_LANGID_NORWEGIAN_NORWAY_NYNORSK       = 0x0814,
        TT_MS_LANGID_POLISH_POLAND                  = 0x0415,
        TT_MS_LANGID_PORTUGUESE_BRAZIL              = 0x0416,
        TT_MS_LANGID_PORTUGUESE_PORTUGAL            = 0x0816,
        TT_MS_LANGID_ROMANSH_SWITZERLAND            = 0x0417,
        TT_MS_LANGID_ROMANIAN_ROMANIA               = 0x0418,
        TT_MS_LANGID_RUSSIAN_RUSSIA                 = 0x0419,
        TT_MS_LANGID_CROATIAN_CROATIA               = 0x041A,
        TT_MS_LANGID_SERBIAN_SERBIA_LATIN           = 0x081A,
        TT_MS_LANGID_SERBIAN_SERBIA_CYRILLIC        = 0x0C1A,
        TT_MS_LANGID_CROATIAN_BOSNIA_HERZEGOVINA    = 0x101A,
        TT_MS_LANGID_BOSNIAN_BOSNIA_HERZEGOVINA     = 0x141A,
        TT_MS_LANGID_SERBIAN_BOSNIA_HERZ_LATIN      = 0x181A,
        TT_MS_LANGID_SERBIAN_BOSNIA_HERZ_CYRILLIC   = 0x1C1A,
        TT_MS_LANGID_BOSNIAN_BOSNIA_HERZ_CYRILLIC   = 0x201A,
        TT_MS_LANGID_SLOVAK_SLOVAKIA                = 0x041B,
        TT_MS_LANGID_ALBANIAN_ALBANIA               = 0x041C,
        TT_MS_LANGID_SWEDISH_SWEDEN                 = 0x041D,
        TT_MS_LANGID_SWEDISH_FINLAND                = 0x081D,
        TT_MS_LANGID_THAI_THAILAND                  = 0x041E,
        TT_MS_LANGID_TURKISH_TURKEY                 = 0x041F,
        TT_MS_LANGID_URDU_PAKISTAN                  = 0x0420,
        TT_MS_LANGID_INDONESIAN_INDONESIA           = 0x0421,
        TT_MS_LANGID_UKRAINIAN_UKRAINE              = 0x0422,
        TT_MS_LANGID_BELARUSIAN_BELARUS             = 0x0423,
        TT_MS_LANGID_SLOVENIAN_SLOVENIA             = 0x0424,
        TT_MS_LANGID_ESTONIAN_ESTONIA               = 0x0425,
        TT_MS_LANGID_LATVIAN_LATVIA                 = 0x0426,
        TT_MS_LANGID_LITHUANIAN_LITHUANIA           = 0x0427,
        TT_MS_LANGID_TAJIK_TAJIKISTAN               = 0x0428,
        TT_MS_LANGID_VIETNAMESE_VIET_NAM            = 0x042A,
        TT_MS_LANGID_ARMENIAN_ARMENIA               = 0x042B,
        TT_MS_LANGID_AZERI_AZERBAIJAN_LATIN         = 0x042C,
        TT_MS_LANGID_AZERI_AZERBAIJAN_CYRILLIC      = 0x082C,
        TT_MS_LANGID_BASQUE_BASQUE                  = 0x042D,
        TT_MS_LANGID_UPPER_SORBIAN_GERMANY          = 0x042E,
        TT_MS_LANGID_LOWER_SORBIAN_GERMANY          = 0x082E,
        TT_MS_LANGID_MACEDONIAN_MACEDONIA           = 0x042F,
        TT_MS_LANGID_SETSWANA_SOUTH_AFRICA          = 0x0432,
        TT_MS_LANGID_ISIXHOSA_SOUTH_AFRICA          = 0x0434,
        TT_MS_LANGID_ISIZULU_SOUTH_AFRICA           = 0x0435,
        TT_MS_LANGID_AFRIKAANS_SOUTH_AFRICA         = 0x0436,
        TT_MS_LANGID_GEORGIAN_GEORGIA               = 0x0437,
        TT_MS_LANGID_FAEROESE_FAEROE_ISLANDS        = 0x0438,
        TT_MS_LANGID_HINDI_INDIA                    = 0x0439,
        TT_MS_LANGID_MALTESE_MALTA                  = 0x043A,
        TT_MS_LANGID_SAMI_NORTHERN_NORWAY           = 0x043B,
        TT_MS_LANGID_SAMI_NORTHERN_SWEDEN           = 0x083B,
        TT_MS_LANGID_SAMI_NORTHERN_FINLAND          = 0x0C3B,
        TT_MS_LANGID_SAMI_LULE_NORWAY               = 0x103B,
        TT_MS_LANGID_SAMI_LULE_SWEDEN               = 0x143B,
        TT_MS_LANGID_SAMI_SOUTHERN_NORWAY           = 0x183B,
        TT_MS_LANGID_SAMI_SOUTHERN_SWEDEN           = 0x1C3B,
        TT_MS_LANGID_SAMI_SKOLT_FINLAND             = 0x203B,
        TT_MS_LANGID_SAMI_INARI_FINLAND             = 0x243B,
        TT_MS_LANGID_IRISH_IRELAND                  = 0x083C,
        TT_MS_LANGID_MALAY_MALAYSIA                 = 0x043E,
        TT_MS_LANGID_MALAY_BRUNEI_DARUSSALAM        = 0x083E,
        TT_MS_LANGID_KAZAKH_KAZAKHSTAN              = 0x043F,
        TT_MS_LANGID_KYRGYZ_KYRGYZSTAN              = 0x0440,
        TT_MS_LANGID_KISWAHILI_KENYA                = 0x0441,
        TT_MS_LANGID_TURKMEN_TURKMENISTAN           = 0x0442,
        TT_MS_LANGID_UZBEK_UZBEKISTAN_LATIN         = 0x0443,
        TT_MS_LANGID_UZBEK_UZBEKISTAN_CYRILLIC      = 0x0843,
        TT_MS_LANGID_TATAR_RUSSIA                   = 0x0444,
        TT_MS_LANGID_BENGALI_INDIA                  = 0x0445,
        TT_MS_LANGID_BENGALI_BANGLADESH             = 0x0845,
        TT_MS_LANGID_PUNJABI_INDIA                  = 0x0446,
        TT_MS_LANGID_GUJARATI_INDIA                 = 0x0447,
        TT_MS_LANGID_ODIA_INDIA                     = 0x0448,
        TT_MS_LANGID_TAMIL_INDIA                    = 0x0449,
        TT_MS_LANGID_TELUGU_INDIA                   = 0x044A,
        TT_MS_LANGID_KANNADA_INDIA                  = 0x044B,
        TT_MS_LANGID_MALAYALAM_INDIA                = 0x044C,
        TT_MS_LANGID_ASSAMESE_INDIA                 = 0x044D,
        TT_MS_LANGID_MARATHI_INDIA                  = 0x044E,
        TT_MS_LANGID_SANSKRIT_INDIA                 = 0x044F,
        TT_MS_LANGID_MONGOLIAN_MONGOLIA             = 0x0450,
        TT_MS_LANGID_MONGOLIAN_PRC                  = 0x0850,
        TT_MS_LANGID_TIBETAN_PRC                    = 0x0451,
        TT_MS_LANGID_WELSH_UNITED_KINGDOM           = 0x0452,
        TT_MS_LANGID_KHMER_CAMBODIA                 = 0x0453,
        TT_MS_LANGID_LAO_LAOS                       = 0x0454,
        TT_MS_LANGID_GALICIAN_GALICIAN              = 0x0456,
        TT_MS_LANGID_KONKANI_INDIA                  = 0x0457,
        TT_MS_LANGID_SYRIAC_SYRIA                   = 0x045A,
        TT_MS_LANGID_SINHALA_SRI_LANKA              = 0x045B,
        TT_MS_LANGID_INUKTITUT_CANADA               = 0x045D,
        TT_MS_LANGID_INUKTITUT_CANADA_LATIN         = 0x085D,
        TT_MS_LANGID_AMHARIC_ETHIOPIA               = 0x045E,
        TT_MS_LANGID_TAMAZIGHT_ALGERIA              = 0x085F,
        TT_MS_LANGID_NEPALI_NEPAL                   = 0x0461,
        TT_MS_LANGID_FRISIAN_NETHERLANDS            = 0x0462,
        TT_MS_LANGID_PASHTO_AFGHANISTAN             = 0x0463,
        TT_MS_LANGID_FILIPINO_PHILIPPINES           = 0x0464,
        TT_MS_LANGID_DHIVEHI_MALDIVES               = 0x0465,
        TT_MS_LANGID_HAUSA_NIGERIA                  = 0x0468,
        TT_MS_LANGID_YORUBA_NIGERIA                 = 0x046A,
        TT_MS_LANGID_QUECHUA_BOLIVIA                = 0x046B,
        TT_MS_LANGID_QUECHUA_ECUADOR                = 0x086B,
        TT_MS_LANGID_QUECHUA_PERU                   = 0x0C6B,
        TT_MS_LANGID_SESOTHO_SA_LEBOA_SOUTH_AFRICA  = 0x046C,
        TT_MS_LANGID_BASHKIR_RUSSIA                 = 0x046D,
        TT_MS_LANGID_LUXEMBOURGISH_LUXEMBOURG       = 0x046E,
        TT_MS_LANGID_GREENLANDIC_GREENLAND          = 0x046F,
        TT_MS_LANGID_IGBO_NIGERIA                   = 0x0470,
        TT_MS_LANGID_YI_PRC                         = 0x0478,
        TT_MS_LANGID_MAPUDUNGUN_CHILE               = 0x047A,
        TT_MS_LANGID_MOHAWK_MOHAWK                  = 0x047C,
        TT_MS_LANGID_BRETON_FRANCE                  = 0x047E,
        TT_MS_LANGID_UIGHUR_PRC                     = 0x0480,
        TT_MS_LANGID_MAORI_NEW_ZEALAND              = 0x0481,
        TT_MS_LANGID_OCCITAN_FRANCE                 = 0x0482,
        TT_MS_LANGID_CORSICAN_FRANCE                = 0x0483,
        TT_MS_LANGID_ALSATIAN_FRANCE                = 0x0484,
        TT_MS_LANGID_YAKUT_RUSSIA                   = 0x0485,
        TT_MS_LANGID_KICHE_GUATEMALA                = 0x0486,
        TT_MS_LANGID_KINYARWANDA_RWANDA             = 0x0487,
        TT_MS_LANGID_WOLOF_SENEGAL                  = 0x0488,
        TT_MS_LANGID_DARI_AFGHANISTAN               = 0x048C;

    int
        TT_NAME_ID_COPYRIGHT             = 0,
        TT_NAME_ID_FONT_FAMILY           = 1,
        TT_NAME_ID_FONT_SUBFAMILY        = 2,
        TT_NAME_ID_UNIQUE_ID             = 3,
        TT_NAME_ID_FULL_NAME             = 4,
        TT_NAME_ID_VERSION_STRING        = 5,
        TT_NAME_ID_PS_NAME               = 6,
        TT_NAME_ID_TRADEMARK             = 7,
        TT_NAME_ID_MANUFACTURER          = 8,
        TT_NAME_ID_DESIGNER              = 9,
        TT_NAME_ID_DESCRIPTION           = 10,
        TT_NAME_ID_VENDOR_URL            = 11,
        TT_NAME_ID_DESIGNER_URL          = 12,
        TT_NAME_ID_LICENSE               = 13,
        TT_NAME_ID_LICENSE_URL           = 14,
        TT_NAME_ID_TYPOGRAPHIC_FAMILY    = 16,
        TT_NAME_ID_TYPOGRAPHIC_SUBFAMILY = 17,
        TT_NAME_ID_MAC_FULL_NAME         = 18,
        TT_NAME_ID_SAMPLE_TEXT           = 19,
        TT_NAME_ID_CID_FINDFONT_NAME     = 20,
        TT_NAME_ID_WWS_FAMILY            = 21,
        TT_NAME_ID_WWS_SUBFAMILY         = 22,
        TT_NAME_ID_LIGHT_BACKGROUND      = 23,
        TT_NAME_ID_DARK_BACKGROUND       = 24,
        TT_NAME_ID_VARIATIONS_PREFIX     = 25;

    int
        TT_UCR_BASIC_LATIN                      = 1 <<  0,
        TT_UCR_LATIN1_SUPPLEMENT                = 1 <<  1,
        TT_UCR_LATIN_EXTENDED_A                 = 1 <<  2,
        TT_UCR_LATIN_EXTENDED_B                 = 1 <<  3,
        TT_UCR_IPA_EXTENSIONS                   = 1 <<  4,
        TT_UCR_SPACING_MODIFIER                 = 1 <<  5,
        TT_UCR_COMBINING_DIACRITICAL_MARKS      = 1 <<  6,
        TT_UCR_GREEK                            = 1 <<  7,
        TT_UCR_COPTIC                           = 1 <<  8,
        TT_UCR_CYRILLIC                         = 1 <<  9,
        TT_UCR_ARMENIAN                         = 1 << 10,
        TT_UCR_HEBREW                           = 1 << 11,
        TT_UCR_VAI                              = 1 << 12,
        TT_UCR_ARABIC                           = 1 << 13,
        TT_UCR_NKO                              = 1 << 14,
        TT_UCR_DEVANAGARI                       = 1 << 15,
        TT_UCR_BENGALI                          = 1 << 16,
        TT_UCR_GURMUKHI                         = 1 << 17,
        TT_UCR_GUJARATI                         = 1 << 18,
        TT_UCR_ORIYA                            = 1 << 19,
        TT_UCR_TAMIL                            = 1 << 20,
        TT_UCR_TELUGU                           = 1 << 21,
        TT_UCR_KANNADA                          = 1 << 22,
        TT_UCR_MALAYALAM                        = 1 << 23,
        TT_UCR_THAI                             = 1 << 24,
        TT_UCR_LAO                              = 1 << 25,
        TT_UCR_GEORGIAN                         = 1 << 26,
        TT_UCR_BALINESE                         = 1 << 27,
        TT_UCR_HANGUL_JAMO                      = 1 << 28,
        TT_UCR_LATIN_EXTENDED_ADDITIONAL        = 1 << 29,
        TT_UCR_GREEK_EXTENDED                   = 1 << 30,
        TT_UCR_GENERAL_PUNCTUATION              = 1 << 31,
        TT_UCR_SUPERSCRIPTS_SUBSCRIPTS          = 1 <<  0,
        TT_UCR_CURRENCY_SYMBOLS                 = 1 <<  1,
        TT_UCR_COMBINING_DIACRITICAL_MARKS_SYMB = 1 <<  2,
        TT_UCR_LETTERLIKE_SYMBOLS               = 1 <<  3,
        TT_UCR_NUMBER_FORMS                     = 1 <<  4,
        TT_UCR_ARROWS                           = 1 <<  5,
        TT_UCR_MATHEMATICAL_OPERATORS           = 1 <<  6,
        TT_UCR_MISCELLANEOUS_TECHNICAL          = 1 <<  7,
        TT_UCR_CONTROL_PICTURES                 = 1 <<  8,
        TT_UCR_OCR                              = 1 <<  9,
        TT_UCR_ENCLOSED_ALPHANUMERICS           = 1 << 10,
        TT_UCR_BOX_DRAWING                      = 1 << 11,
        TT_UCR_BLOCK_ELEMENTS                   = 1 << 12,
        TT_UCR_GEOMETRIC_SHAPES                 = 1 << 13,
        TT_UCR_MISCELLANEOUS_SYMBOLS            = 1 << 14,
        TT_UCR_DINGBATS                         = 1 << 15,
        TT_UCR_CJK_SYMBOLS                      = 1 << 16,
        TT_UCR_HIRAGANA                         = 1 << 17,
        TT_UCR_KATAKANA                         = 1 << 18,
        TT_UCR_BOPOMOFO                         = 1 << 19,
        TT_UCR_HANGUL_COMPATIBILITY_JAMO        = 1 << 20,
        TT_UCR_CJK_MISC                         = 1 << 21,
        TT_UCR_ENCLOSED_CJK_LETTERS_MONTHS      = 1 << 22,
        TT_UCR_CJK_COMPATIBILITY                = 1 << 23,
        TT_UCR_HANGUL                           = 1 << 24,
        TT_UCR_SURROGATES                       = 1 << 25,
        TT_UCR_PHOENICIAN                       = 1 << 26,
        TT_UCR_CJK_UNIFIED_IDEOGRAPHS           = 1 << 27,
        TT_UCR_PRIVATE_USE                      = 1 << 28,
        TT_UCR_CJK_COMPATIBILITY_IDEOGRAPHS     = 1 << 29,
        TT_UCR_ALPHABETIC_PRESENTATION_FORMS    = 1 << 30,
        TT_UCR_ARABIC_PRESENTATION_FORMS_A      = 1 << 31,
        TT_UCR_COMBINING_HALF_MARKS             = 1 <<  0,
        TT_UCR_CJK_COMPATIBILITY_FORMS          = 1 <<  1,
        TT_UCR_SMALL_FORM_VARIANTS              = 1 <<  2,
        TT_UCR_ARABIC_PRESENTATION_FORMS_B      = 1 <<  3,
        TT_UCR_HALFWIDTH_FULLWIDTH_FORMS        = 1 <<  4,
        TT_UCR_SPECIALS                         = 1 <<  5,
        TT_UCR_TIBETAN                          = 1 <<  6,
        TT_UCR_SYRIAC                           = 1 <<  7,
        TT_UCR_THAANA                           = 1 <<  8,
        TT_UCR_SINHALA                          = 1 <<  9,
        TT_UCR_MYANMAR                          = 1 << 10,
        TT_UCR_ETHIOPIC                         = 1 << 11,
        TT_UCR_CHEROKEE                         = 1 << 12,
        TT_UCR_CANADIAN_ABORIGINAL_SYLLABICS    = 1 << 13,
        TT_UCR_OGHAM                            = 1 << 14,
        TT_UCR_RUNIC                            = 1 << 15,
        TT_UCR_KHMER                            = 1 << 16,
        TT_UCR_MONGOLIAN                        = 1 << 17,
        TT_UCR_BRAILLE                          = 1 << 18,
        TT_UCR_YI                               = 1 << 19,
        TT_UCR_PHILIPPINE                       = 1 << 20,
        TT_UCR_OLD_ITALIC                       = 1 << 21,
        TT_UCR_GOTHIC                           = 1 << 22,
        TT_UCR_DESERET                          = 1 << 23,
        TT_UCR_MUSICAL_SYMBOLS                  = 1 << 24,
        TT_UCR_MATH_ALPHANUMERIC_SYMBOLS        = 1 << 25,
        TT_UCR_PRIVATE_USE_SUPPLEMENTARY        = 1 << 26,
        TT_UCR_VARIATION_SELECTORS              = 1 << 27,
        TT_UCR_TAGS                             = 1 << 28,
        TT_UCR_LIMBU                            = 1 << 29,
        TT_UCR_TAI_LE                           = 1 << 30,
        TT_UCR_NEW_TAI_LUE                      = 1 << 31,
        TT_UCR_BUGINESE                         = 1 <<  0,
        TT_UCR_GLAGOLITIC                       = 1 <<  1,
        TT_UCR_TIFINAGH                         = 1 <<  2,
        TT_UCR_YIJING                           = 1 <<  3,
        TT_UCR_SYLOTI_NAGRI                     = 1 <<  4,
        TT_UCR_LINEAR_B                         = 1 <<  5,
        TT_UCR_ANCIENT_GREEK_NUMBERS            = 1 <<  6,
        TT_UCR_UGARITIC                         = 1 <<  7,
        TT_UCR_OLD_PERSIAN                      = 1 <<  8,
        TT_UCR_SHAVIAN                          = 1 <<  9,
        TT_UCR_OSMANYA                          = 1 << 10,
        TT_UCR_CYPRIOT_SYLLABARY                = 1 << 11,
        TT_UCR_KHAROSHTHI                       = 1 << 12,
        TT_UCR_TAI_XUAN_JING                    = 1 << 13,
        TT_UCR_CUNEIFORM                        = 1 << 14,
        TT_UCR_COUNTING_ROD_NUMERALS            = 1 << 15,
        TT_UCR_SUNDANESE                        = 1 << 16,
        TT_UCR_LEPCHA                           = 1 << 17,
        TT_UCR_OL_CHIKI                         = 1 << 18,
        TT_UCR_SAURASHTRA                       = 1 << 19,
        TT_UCR_KAYAH_LI                         = 1 << 20,
        TT_UCR_REJANG                           = 1 << 21,
        TT_UCR_CHAM                             = 1 << 22,
        TT_UCR_ANCIENT_SYMBOLS                  = 1 << 23,
        TT_UCR_PHAISTOS_DISC                    = 1 << 24,
        TT_UCR_OLD_ANATOLIAN                    = 1 << 25,
        TT_UCR_GAME_TILES                       = 1 << 26;

    int
        FT_SFNT_HEAD = 0,
        FT_SFNT_MAXP = 1,
        FT_SFNT_OS2  = 2,
        FT_SFNT_HHEA = 3,
        FT_SFNT_VHEA = 4,
        FT_SFNT_POST = 5,
        FT_SFNT_PCLT = 6,
        FT_SFNT_MAX  = 7;

    int
        TTAG_avar    = FT_MAKE_TAG( 'a', 'v', 'a', 'r' ),
        TTAG_BASE    = FT_MAKE_TAG( 'B', 'A', 'S', 'E' ),
        TTAG_bdat    = FT_MAKE_TAG( 'b', 'd', 'a', 't' ),
        TTAG_BDF     = FT_MAKE_TAG( 'B', 'D', 'F', ' ' ),
        TTAG_bhed    = FT_MAKE_TAG( 'b', 'h', 'e', 'd' ),
        TTAG_bloc    = FT_MAKE_TAG( 'b', 'l', 'o', 'c' ),
        TTAG_bsln    = FT_MAKE_TAG( 'b', 's', 'l', 'n' ),
        TTAG_CBDT    = FT_MAKE_TAG( 'C', 'B', 'D', 'T' ),
        TTAG_CBLC    = FT_MAKE_TAG( 'C', 'B', 'L', 'C' ),
        TTAG_CFF     = FT_MAKE_TAG( 'C', 'F', 'F', ' ' ),
        TTAG_CFF2    = FT_MAKE_TAG( 'C', 'F', 'F', '2' ),
        TTAG_CID     = FT_MAKE_TAG( 'C', 'I', 'D', ' ' ),
        TTAG_cmap    = FT_MAKE_TAG( 'c', 'm', 'a', 'p' ),
        TTAG_COLR    = FT_MAKE_TAG( 'C', 'O', 'L', 'R' ),
        TTAG_CPAL    = FT_MAKE_TAG( 'C', 'P', 'A', 'L' ),
        TTAG_cvar    = FT_MAKE_TAG( 'c', 'v', 'a', 'r' ),
        TTAG_cvt     = FT_MAKE_TAG( 'c', 'v', 't', ' ' ),
        TTAG_DSIG    = FT_MAKE_TAG( 'D', 'S', 'I', 'G' ),
        TTAG_EBDT    = FT_MAKE_TAG( 'E', 'B', 'D', 'T' ),
        TTAG_EBLC    = FT_MAKE_TAG( 'E', 'B', 'L', 'C' ),
        TTAG_EBSC    = FT_MAKE_TAG( 'E', 'B', 'S', 'C' ),
        TTAG_feat    = FT_MAKE_TAG( 'f', 'e', 'a', 't' ),
        TTAG_FOND    = FT_MAKE_TAG( 'F', 'O', 'N', 'D' ),
        TTAG_fpgm    = FT_MAKE_TAG( 'f', 'p', 'g', 'm' ),
        TTAG_fvar    = FT_MAKE_TAG( 'f', 'v', 'a', 'r' ),
        TTAG_gasp    = FT_MAKE_TAG( 'g', 'a', 's', 'p' ),
        TTAG_GDEF    = FT_MAKE_TAG( 'G', 'D', 'E', 'F' ),
        TTAG_glyf    = FT_MAKE_TAG( 'g', 'l', 'y', 'f' ),
        TTAG_GPOS    = FT_MAKE_TAG( 'G', 'P', 'O', 'S' ),
        TTAG_GSUB    = FT_MAKE_TAG( 'G', 'S', 'U', 'B' ),
        TTAG_gvar    = FT_MAKE_TAG( 'g', 'v', 'a', 'r' ),
        TTAG_HVAR    = FT_MAKE_TAG( 'H', 'V', 'A', 'R' ),
        TTAG_hdmx    = FT_MAKE_TAG( 'h', 'd', 'm', 'x' ),
        TTAG_head    = FT_MAKE_TAG( 'h', 'e', 'a', 'd' ),
        TTAG_hhea    = FT_MAKE_TAG( 'h', 'h', 'e', 'a' ),
        TTAG_hmtx    = FT_MAKE_TAG( 'h', 'm', 't', 'x' ),
        TTAG_JSTF    = FT_MAKE_TAG( 'J', 'S', 'T', 'F' ),
        TTAG_just    = FT_MAKE_TAG( 'j', 'u', 's', 't' ),
        TTAG_kern    = FT_MAKE_TAG( 'k', 'e', 'r', 'n' ),
        TTAG_lcar    = FT_MAKE_TAG( 'l', 'c', 'a', 'r' ),
        TTAG_loca    = FT_MAKE_TAG( 'l', 'o', 'c', 'a' ),
        TTAG_LTSH    = FT_MAKE_TAG( 'L', 'T', 'S', 'H' ),
        TTAG_LWFN    = FT_MAKE_TAG( 'L', 'W', 'F', 'N' ),
        TTAG_MATH    = FT_MAKE_TAG( 'M', 'A', 'T', 'H' ),
        TTAG_maxp    = FT_MAKE_TAG( 'm', 'a', 'x', 'p' ),
        TTAG_META    = FT_MAKE_TAG( 'M', 'E', 'T', 'A' ),
        TTAG_MMFX    = FT_MAKE_TAG( 'M', 'M', 'F', 'X' ),
        TTAG_MMSD    = FT_MAKE_TAG( 'M', 'M', 'S', 'D' ),
        TTAG_mort    = FT_MAKE_TAG( 'm', 'o', 'r', 't' ),
        TTAG_morx    = FT_MAKE_TAG( 'm', 'o', 'r', 'x' ),
        TTAG_MVAR    = FT_MAKE_TAG( 'M', 'V', 'A', 'R' ),
        TTAG_name    = FT_MAKE_TAG( 'n', 'a', 'm', 'e' ),
        TTAG_opbd    = FT_MAKE_TAG( 'o', 'p', 'b', 'd' ),
        TTAG_OS2     = FT_MAKE_TAG( 'O', 'S', '/', '2' ),
        TTAG_OTTO    = FT_MAKE_TAG( 'O', 'T', 'T', 'O' ),
        TTAG_PCLT    = FT_MAKE_TAG( 'P', 'C', 'L', 'T' ),
        TTAG_POST    = FT_MAKE_TAG( 'P', 'O', 'S', 'T' ),
        TTAG_post    = FT_MAKE_TAG( 'p', 'o', 's', 't' ),
        TTAG_prep    = FT_MAKE_TAG( 'p', 'r', 'e', 'p' ),
        TTAG_prop    = FT_MAKE_TAG( 'p', 'r', 'o', 'p' ),
        TTAG_sbix    = FT_MAKE_TAG( 's', 'b', 'i', 'x' ),
        TTAG_sfnt    = FT_MAKE_TAG( 's', 'f', 'n', 't' ),
        TTAG_SING    = FT_MAKE_TAG( 'S', 'I', 'N', 'G' ),
        TTAG_SVG     = FT_MAKE_TAG( 'S', 'V', 'G', ' ' ),
        TTAG_trak    = FT_MAKE_TAG( 't', 'r', 'a', 'k' ),
        TTAG_true    = FT_MAKE_TAG( 't', 'r', 'u', 'e' ),
        TTAG_ttc     = FT_MAKE_TAG( 't', 't', 'c', ' ' ),
        TTAG_ttcf    = FT_MAKE_TAG( 't', 't', 'c', 'f' ),
        TTAG_TYP1    = FT_MAKE_TAG( 'T', 'Y', 'P', '1' ),
        TTAG_typ1    = FT_MAKE_TAG( 't', 'y', 'p', '1' ),
        TTAG_VDMX    = FT_MAKE_TAG( 'V', 'D', 'M', 'X' ),
        TTAG_vhea    = FT_MAKE_TAG( 'v', 'h', 'e', 'a' ),
        TTAG_vmtx    = FT_MAKE_TAG( 'v', 'm', 't', 'x' ),
        TTAG_VVAR    = FT_MAKE_TAG( 'V', 'V', 'A', 'R' ),
        TTAG_wOFF    = FT_MAKE_TAG( 'w', 'O', 'F', 'F' ),
        TTAG_wOF2    = FT_MAKE_TAG( 'w', 'O', 'F', '2' ),
        TTAG_0xA5kbd = FT_MAKE_TAG( 0xA5, 'k', 'b', 'd' ),
        TTAG_0xA5lst = FT_MAKE_TAG( 0xA5, 'l', 's', 't' );
    

    
    int FT_Init_FreeType(PointerBuffer alibrary);
    int FT_Done_FreeType(long library);

    int FT_New_Face(PointerBuffer aface);

    int FT_New_Face(PointerBuffer aface);
    int FT_New_Memory_Face(PointerBuffer aface);
    int FT_Open_Face( PointerBuffer aface);
    int FT_Attach_File(FT_Face face,  ByteBuffer filepathname);
    
    int FT_Attach_File(FT_Face face,  CharSequence filepathname);
    int FT_Attach_Stream(FT_Face face,  FT_Open_Args parameters);
    int FT_Reference_Face(FT_Face face);
    int FT_Done_Face(FT_Face face);
    int FT_Select_Size(FT_Face face,  int strike_index);
    int FT_Request_Size(FT_Face face, FT_Size_Request req);
    int FT_Set_Char_Size(FT_Face face,  int vert_resolution);
    int FT_Set_Pixel_Sizes(FT_Face face,  int pixel_height);
    int FT_Load_Glyph(FT_Face face,  int load_flags);
    int FT_Load_Char(FT_Face face,  int load_flags);
    void FT_Set_Transform(FT_Face face,  @Nullable FT_Vector delta);
    void FT_Get_Transform(FT_Face face,  @Nullable FT_Vector delta);
    int FT_Render_Glyph(FT_GlyphSlot slot,  int render_mode);
    int FT_Get_Kerning(FT_Face face,  FT_Vector akerning);
    int FT_Get_Track_Kerning(FT_Face face,  CLongBuffer akerning);
    int FT_Select_Charmap(FT_Face face,  int encoding);
    int FT_Set_Charmap(FT_Face face, FT_CharMap charmap);
    int FT_Get_Charmap_Index(FT_CharMap charmap);
    int FT_Get_Char_Index(FT_Face face,  long charcode);
    long FT_Get_First_Char(FT_Face face,  IntBuffer agindex);
    long FT_Get_Next_Char(FT_Face face,  IntBuffer agindex);
    int FT_Face_Properties(FT_Face face,  FT_Parameter.Buffer properties);
    int FT_Get_Name_Index(FT_Face face,  ByteBuffer glyph_name);
    int FT_Get_Name_Index(FT_Face face,  CharSequence glyph_name);
    int FT_Get_Glyph_Name(FT_Face face,  ByteBuffer buffer);
    @Nullable String FT_Get_Postscript_Name(FT_Face face);
    int FT_Get_SubGlyph_Info(FT_GlyphSlot glyph,  FT_Matrix p_transform);
    short FT_Get_FSType_Flags(FT_Face face);
    int FT_Face_GetCharVariantIndex(FT_Face face,  long variantSelector);
    int FT_Face_GetCharVariantIsDefault(FT_Face face,  long variantSelector);
    long FT_Face_GetVariantSelectors(FT_Face face);
    long FT_Face_GetVariantsOfChar(FT_Face face,  long charcode);
    long FT_Face_GetCharsOfVariant(FT_Face face,  long variantSelector);
    long FT_MulDiv( long c);
    long FT_MulFix( long b);
    long FT_DivFix( long b);
    long FT_RoundFix( long a);
    long FT_CeilFix( long a);
    long FT_FloorFix( long a);
    void FT_Vector_Transform( FT_Matrix matrix);
    void FT_Library_Version( IntBuffer apatch);
    boolean FT_Face_CheckTrueTypePatents(FT_Face face);
    boolean FT_Face_SetUnpatentedHinting(FT_Face face,  boolean value);
    int FT_Get_Advance(FT_Face face,  CLongBuffer padvance);
    int FT_Get_Advances(FT_Face face,  CLongBuffer padvances);
    int FT_Outline_Get_BBox( FT_BBox abbox);
    int FT_Get_BDF_Charset_ID(FT_Face face,  PointerBuffer acharset_registry);
    int FT_Get_BDF_Property(FT_Face face,  BDF_Property aproperty);
    int FT_Get_BDF_Property(FT_Face face,  BDF_Property aproperty);
    void FT_Bitmap_Init( FT_Bitmap abitmap);
    int FT_Bitmap_Copy( FT_Bitmap target);
    int FT_Bitmap_Embolden( long yStrength);
    int FT_Bitmap_Convert( int alignment);
    int FT_Bitmap_Blend( FT_Vector atarget_offset, FT_Color color);
    int FT_GlyphSlot_Own_Bitmap(FT_GlyphSlot slot);
    int FT_Bitmap_Done( FT_Bitmap bitmap);
    int FT_Stream_OpenBzip2(FT_Stream stream, FT_Stream source);
    int FTC_Manager_New( PointerBuffer amanager);
    void FTC_Manager_Reset( long manager);
    void FTC_Manager_Done( long manager);
    int FTC_Manager_LookupFace( PointerBuffer aface);
    int FTC_Manager_LookupSize( PointerBuffer asize);
    void FTC_Node_Unref( long manager);
    void FTC_Manager_RemoveFaceID( long face_id);
    int FTC_CMapCache_New( PointerBuffer acache);
    int FTC_CMapCache_Lookup( int char_code);
    int FTC_ImageCache_New( PointerBuffer acache);
    int FTC_ImageCache_Lookup( @Nullable PointerBuffer anode);
    int FTC_ImageCache_LookupScaler( @Nullable PointerBuffer anode);
    int FTC_SBitCache_New( PointerBuffer acache);
    int FTC_SBitCache_Lookup( @Nullable PointerBuffer anode);
    int FTC_SBitCache_LookupScaler( @Nullable PointerBuffer anode);
    int FT_Get_CID_Registry_Ordering_Supplement(FT_Face face,  IntBuffer supplement);
    int FT_Get_CID_Is_Internally_CID_Keyed(FT_Face face,  ByteBuffer is_cid);
    int FT_Get_CID_From_Glyph_Index(FT_Face face,  IntBuffer cid);
    int FT_Palette_Data_Get(FT_Face face,  FT_Palette_Data apalette);
    int FT_Palette_Select(FT_Face face,  @Nullable PointerBuffer apalette);
    int FT_Palette_Set_Foreground_Color(FT_Face face, FT_Color foreground_color);
    boolean FT_Get_Color_Glyph_Layer(FT_Face face,  FT_LayerIterator iterator);
    boolean FT_Get_Color_Glyph_Paint(FT_Face face,  FT_OpaquePaint paint);
    boolean FT_Get_Color_Glyph_ClipBox(FT_Face face,  FT_ClipBox clip_box);
    boolean FT_Get_Paint_Layers(FT_Face face,  FT_OpaquePaint paint);
    boolean FT_Get_Colorline_Stops(FT_Face face,  FT_ColorStopIterator iterator);
    boolean FT_Get_Paint(FT_Face face,  FT_COLR_Paint paint);
    @Nullable String FT_Error_String( int error_code);
    
    @Nullable String FT_Get_Font_Format(FT_Face face);
    
    int FT_Get_Gasp(FT_Face face,  int ppem);
    int FT_New_Glyph( PointerBuffer aglyph);
    int FT_Get_Glyph(FT_GlyphSlot slot,  PointerBuffer aglyph);
    int FT_Glyph_Copy(FT_Glyph source,  PointerBuffer target);
    int FT_Glyph_Transform(FT_Glyph glyph,  @Nullable FT_Vector delta);
    void FT_Glyph_Get_CBox(FT_Glyph glyph,  FT_BBox acbox);
    int FT_Glyph_To_Bitmap( boolean destroy);
    void FT_Done_Glyph( @Nullable FT_Glyph glyph);
    void FT_Matrix_Multiply( FT_Matrix b);
    
    int FT_Matrix_Invert( FT_Matrix matrix);
    int FT_TrueTypeGX_Validate(FT_Face face,  PointerBuffer tables);
    void FT_TrueTypeGX_Free(FT_Face face,  ByteBuffer table);
    int FT_ClassicKern_Validate(FT_Face face,  PointerBuffer ckern_table);
    void FT_ClassicKern_Free(FT_Face face,  ByteBuffer table);
    int FT_Stream_OpenGzip(FT_Stream stream, FT_Stream source);
    int FT_Gzip_Uncompress(FT_Memory memory,  ByteBuffer input);
    
    int FT_Library_SetLcdFilter( int filter);
    
    int FT_Library_SetLcdFilterWeights( ByteBuffer weights);
    
    int FT_Library_SetLcdGeometry( FT_Vector.Buffer sub);
    
    @Nullable FT_ListNode FT_List_Find(FT_List list,  long data);

    /** {@code void FT_List_Add(FT_List list, FT_ListNode node)} */
    void FT_List_Add(FT_List list, FT_ListNode node);


    void FT_List_Insert(FT_List list, FT_ListNode node);
    void FT_List_Remove(FT_List list, FT_ListNode node);
    void FT_List_Up(FT_List list, FT_ListNode node);
    
    int FT_List_Iterate(FT_List list,  long user);
    void FT_List_Finalize(FT_List list,  long user);
    
    int FT_Stream_OpenLZW(FT_Stream stream, FT_Stream source);
    int FT_Get_Multi_Master(FT_Face face,  FT_Multi_Master amaster);
    int FT_Get_MM_Var(FT_Face face,  PointerBuffer amaster);
    int FT_Done_MM_Var( FT_MM_Var amaster);
    int FT_Set_MM_Design_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Set_Var_Design_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Get_Var_Design_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Set_MM_Blend_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Get_MM_Blend_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Set_Var_Blend_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Get_Var_Blend_Coordinates(FT_Face face,  CLongBuffer coords);
    int FT_Set_MM_WeightVector(FT_Face face,  @Nullable CLongBuffer weightvector);
    int FT_Get_MM_WeightVector(FT_Face face,  CLongBuffer weightvector);
    int FT_Get_Var_Axis_Flags( IntBuffer flags);
    int FT_Set_Named_Instance(FT_Face face,  int instance_index);
    int FT_Get_Default_Named_Instance(FT_Face face,  IntBuffer instance_index);

    int FT_Add_Module( FT_Module_Class clazz);

    long FT_Get_Module( ByteBuffer module_name);
    long FT_Get_Module( CharSequence module_name);
    int FT_Remove_Module( long module);
    int FT_Property_Set( ByteBuffer value);
    int FT_Property_Set( ByteBuffer value);
    int FT_Property_Get( ByteBuffer value);
    int FT_Property_Get( ByteBuffer value);
    void FT_Set_Default_Properties( long library);
    int FT_Reference_Library( long library);
    
    int FT_New_Library(FT_Memory memory,  PointerBuffer alibrary);
    
    int FT_Done_Library( long library);
    void FT_Set_Debug_Hook( @Nullable FT_DebugHook_FuncI debug_hook);
    void FT_Add_Default_Modules( long library);
    int FT_Get_TrueType_Engine_Type( long library);
    int FT_OpenType_Validate(FT_Face face,  PointerBuffer JSTF_table);
    void FT_OpenType_Free(FT_Face face,  ByteBuffer table);
    int FT_Outline_Decompose( long user);
    int FT_Outline_New( FT_Outline anoutline);
    
    int FT_Outline_Done( FT_Outline outline);
    
    int FT_Outline_Check( FT_Outline outline);
    void FT_Outline_Get_CBox( FT_BBox acbox);
    void FT_Outline_Translate( long yOffset);
    int FT_Outline_Copy( FT_Outline target);

    void FT_Outline_Transform( FT_Matrix matrix);
    int FT_Outline_Embolden( long strength);
    int FT_Outline_EmboldenXY( long ystrength);
    void FT_Outline_Reverse( FT_Outline outline);
    int FT_Outline_Get_Bitmap( FT_Bitmap abitmap);
    int FT_Outline_Render( FT_Raster_Params params);
    int FT_Outline_Get_Orientation( FT_Outline outline);
    int FT_Get_PFR_Metrics(FT_Face face,  @Nullable CLongBuffer ametrics_y_scale);
    int FT_Get_PFR_Kerning(FT_Face face,  FT_Vector avector);
    int FT_Get_PFR_Advance(FT_Face face,  CLongBuffer aadvance);
    long FT_Get_Renderer( int format);

    int FT_Set_Renderer( FT_Parameter.Buffer parameters);
    
    int FT_New_Size(FT_Face face,  PointerBuffer size);
    
    int FT_Done_Size(FT_Size size);
    
    int FT_Activate_Size(FT_Size size);
    
    int FT_Get_Sfnt_Name_Count(FT_Face face);
    
    int FT_Get_Sfnt_Name(FT_Face face,  FT_SfntName aname);
    
    int FT_Get_Sfnt_LangTag(FT_Face face,  FT_SfntLangTag alangTag);
    
    int FT_Outline_GetInsideBorder( FT_Outline outline);
    
    int FT_Outline_GetOutsideBorder( FT_Outline outline);
    
    int FT_Stroker_New( PointerBuffer astroker);
    void FT_Stroker_Set( long miter_limit);
    void FT_Stroker_Rewind( long stroker);
    int FT_Stroker_ParseOutline( boolean opened);
    int FT_Stroker_BeginSubPath( boolean open;
    int FT_Stroker_EndSubPath( long stroker);
    int FT_Stroker_LineTo( FT_Vector to);
    int FT_Stroker_ConicTo( FT_Vector to) ;
    int FT_Stroker_CubicTo( FT_Vector to);

    int FT_Stroker_GetBorderCounts( IntBuffer anum_contours);
    void nFT_Stroker_ExportBorder(long stroker, int border, long outline);
    void FT_Stroker_ExportBorder( FT_Outline outline);
    int FT_Stroker_GetCounts( IntBuffer anum_contours);
    void FT_Stroker_Export( FT_Outline outline);
    void FT_Stroker_Done( long stroker);
    int FT_Glyph_Stroke( boolean destroy);
    int FT_Glyph_StrokeBorder( boolean destroy);
    void FT_GlyphSlot_Embolden(FT_GlyphSlot slot);
    void FT_GlyphSlot_AdjustWeight(FT_GlyphSlot slot,  long ydelta);
    void FT_GlyphSlot_Oblique(FT_GlyphSlot slot);
    void FT_GlyphSlot_Slant(FT_GlyphSlot slot,  long yslant);
    
    long FT_Sin( long angle);
    
    long FT_Cos( long angle);
    
    long FT_Tan( long angle);
    
    long FT_Atan2( long y);
    
    long FT_Angle_Diff( long angle2);
    void FT_Vector_Unit( long angle);
    void FT_Vector_Rotate( long angle);
    long FT_Vector_Length( FT_Vector vec);
    void FT_Vector_Polarize( CLongBuffer angle);
    void FT_Vector_From_Polar( long angle);
    int FT_Has_PS_Glyph_Names(FT_Face face);
    int FT_Get_PS_Font_Info(FT_Face face, PS_FontInfo afont_info);
    int FT_Get_PS_Font_Private(FT_Face face,  long afont_private);
    long FT_Get_PS_Font_Value(FT_Face face,  @Nullable ByteBuffer value);
    long FT_Get_Sfnt_Table(FT_Face face,  int tag);
    int FT_Load_Sfnt_Table(FT_Face face,  @Nullable CLongBuffer length);
    int FT_Sfnt_Table_Info(FT_Face face,  @Nullable CLongBuffer length);
    long FT_Get_CMap_Language_ID(FT_CharMap charmap);
    long FT_Get_CMap_Format(FT_CharMap charmap);

    static int FT_ENC_TAG(int a, int b, int c, int d) {
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | (d & 0xFF);
    }

    static boolean FT_HAS_HORIZONTAL(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_HORIZONTAL) != 0;
    }

    static boolean FT_HAS_VERTICAL(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_VERTICAL) != 0;
    }

    static boolean FT_HAS_KERNING(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_KERNING) != 0;
    }

    static boolean FT_IS_SCALABLE(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_SCALABLE) != 0;
    }

    static boolean FT_IS_SFNT(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_SFNT) != 0;
    }

    static boolean FT_IS_FIXED_WIDTH(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_FIXED_WIDTH) != 0;
    }

    static boolean FT_HAS_FIXED_SIZES(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_FIXED_SIZES) != 0;
    }

    static boolean FT_HAS_GLYPH_NAMES(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_GLYPH_NAMES) != 0;
    }

    static boolean FT_HAS_MULTIPLE_MASTERS(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_MULTIPLE_MASTERS) != 0;
    }

    static boolean FT_IS_NAMED_INSTANCE(FT_Face face) {
        return (face.face_index() & 0x7FFF0000) != 0;
    }

    static boolean FT_IS_VARIATION(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_VARIATION) != 0;
    }

    static boolean FT_IS_CID_KEYED(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_CID_KEYED) != 0;
    }

    static boolean FT_IS_TRICKY(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_TRICKY) != 0;
    }

    static boolean FT_HAS_COLOR(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_COLOR) != 0;
    }

    static boolean FT_HAS_SVG(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_SVG) != 0;
    }

    static boolean FT_HAS_SBIX(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_SBIX) != 0;
    }

    static boolean FT_HAS_SBIX_OVERLAY(FT_Face face) {
        return (face.face_flags() & FT_FACE_FLAG_SBIX_OVERLAY) != 0;
    }

    static int FT_LOAD_TARGET_(int x) {
        return (x & 15) << 16;
    }

    static int FT_LOAD_TARGET_MODE(int x) {
        return (x >> 16) & 15;
    }

    static boolean FTC_IMAGE_TYPE_COMPARE(FTC_ImageType d1, FTC_ImageType d2) {
        return d1.face_id() == d2.face_id() && d1.width() == d2.width() && d1.flags() == d2.flags();
    }

    static  int FT_CURVE_TAG(int flag) {
        return flag & 0x03;
    }

    static int FT_IMAGE_TAG(int _x1, int _x2, int _x3, int _x4) {
        return ((_x1 & 0xFF) << 24) | ((_x2 & 0xFF) << 16) | ((_x3 & 0xFF) << 8) | (_x4 & 0xFF);
    }

    static int FT_MAKE_TAG(int _x1, int _x2, int _x3, int _x4) {
        return ((_x1 & 0xFF) << 24) | ((_x2 & 0xFF) << 16) | ((_x3 & 0xFF) << 8) | (_x4 & 0xFF);
    }

    static boolean FT_IS_EMPTY(FT_List list) {
        return list.head() == null;
    }

}
