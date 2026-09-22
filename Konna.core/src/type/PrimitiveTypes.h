//
// Created by EgrZver on 22.09.2026.
//

#ifndef KONNA_CORE_PRIMITIVETYPES_H
#define KONNA_CORE_PRIMITIVETYPES_H
#include <cmath>
#include <cstdint>

namespace Konna::Core {

    using ku8 = std::uint8_t;
    using ku16 = std::uint16_t;
    using ku32 = std::uint32_t;
    using ku64 = std::uint64_t;

    using ki8 = std::int8_t;
    using ki16 = std::int16_t;
    using ki32 = std::int32_t;
    using ki64 = std::int64_t;

    using kf32 = std::float_t;
    using kf64 = std::double_t;

    static_assert(sizeof(ki32) == 4, "i32 must be 4 bytes!");
    static_assert(sizeof(kf32) == 4, "f32 must be 4 bytes!");

}

#endif //KONNA_CORE_PRIMITIVETYPES_H
