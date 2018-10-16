package org.jetbrains.plugins.ideavim.extension.visualstarsearch

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import org.jetbrains.plugins.ideavim.VimTestCase

class VimVisualStarSearchExtentionTest : VimTestCase() {
    override fun setUp() {
        super.setUp()
        enableExtensions("visual-star-search")
    }

    fun testVimVisualStarSearchForward() {
        typeTextInFile(parseKeys("vee*"),
                "qwe asd zxc qwe asd zxc <caret>qwe asd zxc qwe asd zxc qwe asd zxc ")
        myFixture.checkResult(
                "qwe asd zxc qwe asd zxc qwe asd zxc <caret>qwe asd zxc qwe asd zxc ")
        assertMode(CommandState.Mode.COMMAND)
    }

    fun testVimVisualStarSearchBackward() {
        typeTextInFile(parseKeys("vee#"),
                "qwe asd zxc qwe asd zxc <caret>qwe asd zxc qwe asd zxc qwe asd zxc ")
        myFixture.checkResult(
                "qwe asd zxc <caret>qwe asd zxc qwe asd zxc qwe asd zxc qwe asd zxc ")
        assertMode(CommandState.Mode.COMMAND)
    }
}
