package com.maddyhome.idea.vim.extension.visualstarsearch

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.extension.VimNonDisposableExtension
import com.maddyhome.idea.vim.key.Shortcut

class VimVisualStarSearchExtention : VimNonDisposableExtension() {
    override fun getName() = "visual-star-search"

    override fun initOnce() {
        registerAction("VimVisualStarSearchForward", VimVisualStarSearchForwardAction(), Command.FLAG_MOT_EXCLUSIVE or Command.FLAG_SAVE_JUMP, "*")
        registerAction("VimVisualStarSearchBackward", VimVisualStarSearchBackwardAction(), Command.FLAG_MOT_EXCLUSIVE or Command.FLAG_SAVE_JUMP, "#")
    }

    private fun registerAction(actionId: String, action: AnAction, commandFlags: Int, key: String) {
        val actionManager = ActionManager.getInstance()
        if (actionManager.getAction(actionId) == null) {
            actionManager.registerAction(actionId, action, VimPlugin.getPluginId())
            VimPlugin.getKey().registerAction(MappingMode.V, actionId, Command.Type.MOTION, commandFlags, Shortcut(key))
        }
    }
}

