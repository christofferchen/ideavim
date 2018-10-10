package com.werfad

class UserConfig {
    data class DataBean(var characters: String = DEFAULT_CHARACTERS,
                        var fontColor: Int = DEFAULT_FONT_COLOR,
                        var bgColor: Int = DEFAULT_BG_COLOR,
                        var smartcase: Boolean = DEFAULT_SMARTCASE)


    companion object {
        const val DEFAULT_CHARACTERS = "abcdefghijklmnopqrstuvwxyz;"
        const val DEFAULT_FONT_COLOR = 0xdeb400
        const val DEFAULT_BG_COLOR = 0x212121
        const val DEFAULT_SMARTCASE = true

        fun getDataBean(): DataBean {
            return DataBean()
        }
    }
}


