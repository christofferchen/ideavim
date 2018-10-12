package com.maddyhome.idea.vim.extension.easymotion

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.editor.Editor
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.extension.VimExtensionFacade.putExtensionHandlerMapping
import com.maddyhome.idea.vim.extension.VimExtensionFacade.putKeyMapping
import com.maddyhome.idea.vim.extension.VimExtensionHandler
import com.maddyhome.idea.vim.extension.VimNonDisposableExtension
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import com.werfad.JumpHandler
import com.werfad.JumpHandler.MODE_CHAR1
import com.werfad.JumpHandler.MODE_LINE
import com.werfad.JumpHandler.MODE_WORD0
import com.werfad.JumpHandler.MODE_WORD1

private const val BD_S_C = "<Plug>easymotion-bd-s-c"
private const val BD_S_W = "<Plug>easymotion-bd-s-w"
private const val BD_W = "<Plug>easymotion-bd-w"
private const val BD_JK = "<Plug>easymotion-bd-jk"

/**
 * integrated from https://github.com/a690700752/KJump
 */
class VimEasyMotionExtension : VimNonDisposableExtension() {
    override fun getName() = "easymotion"

    override fun initOnce() {
        putExtensionHandlerMapping(MappingMode.NVO, parseKeys(BD_S_C), BDSHandler(), false)
        putExtensionHandlerMapping(MappingMode.NVO, parseKeys(BD_S_W), BDSWHandler(), false)
        putExtensionHandlerMapping(MappingMode.NVO, parseKeys(BD_W), BDWHandler(), false)
        putExtensionHandlerMapping(MappingMode.NVO, parseKeys(BD_JK), BDJKHandler(), false)
        putKeyMapping(MappingMode.NVO, parseKeys("<space>?"), parseKeys(BD_S_C), true)
        putKeyMapping(MappingMode.NVO, parseKeys("<space>/"), parseKeys(BD_S_W), true)
        putKeyMapping(MappingMode.NVO, parseKeys("<space>;"), parseKeys(BD_W), true)
        putKeyMapping(MappingMode.NVO, parseKeys("<space><cr>"), parseKeys(BD_JK), true)
    }

}

class BDSWHandler : VimExtensionHandler {
    override fun execute(editor: Editor, context: DataContext) {
        JumpHandler.start(editor, MODE_WORD1)
    }

}

class BDJKHandler : VimExtensionHandler {
    override fun execute(editor: Editor, context: DataContext) {
        JumpHandler.start(editor, MODE_LINE)
    }

}

class BDSHandler : VimExtensionHandler {
    override fun execute(editor: Editor, context: DataContext) {
        JumpHandler.start(editor, MODE_CHAR1)
    }

}

class BDWHandler : VimExtensionHandler {
    override fun execute(editor: Editor, context: DataContext) {
        JumpHandler.start(editor, MODE_WORD0)
    }
}
