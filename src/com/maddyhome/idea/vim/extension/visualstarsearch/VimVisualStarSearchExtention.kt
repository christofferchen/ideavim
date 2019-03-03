package com.maddyhome.idea.vim.extension.visualstarsearch

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.maddyhome.idea.vim.VimPlugin
import com.maddyhome.idea.vim.command.Command
import com.maddyhome.idea.vim.command.CommandFlags
import com.maddyhome.idea.vim.command.MappingMode
import com.maddyhome.idea.vim.extension.VimNonDisposableExtension
import com.maddyhome.idea.vim.key.Shortcut
import java.util.*

class VimVisualStarSearchExtention : VimNonDisposableExtension() {
    override fun getName() = "visual-star-search"

    override fun initOnce() {
        registerAction("VimVisualStarSearchForward", VimVisualStarSearchForwardAction(), EnumSet.of(CommandFlags.FLAG_MOT_EXCLUSIVE, CommandFlags.FLAG_SAVE_JUMP), "*")
        registerAction("VimVisualStarSearchBackward", VimVisualStarSearchBackwardAction(), EnumSet.of(CommandFlags.FLAG_MOT_EXCLUSIVE, CommandFlags.FLAG_SAVE_JUMP), "#")
    }

    private fun registerAction(actionId: String, action: AnAction, commandFlags: EnumSet<CommandFlags>, key: String) {
        val actionManager = ActionManager.getInstance()
        if (actionManager.getAction(actionId) == null) {
            actionManager.registerAction(actionId, action, VimPlugin.getPluginId())
            VimPlugin.getKey().registerAction(MappingMode.V, actionId, Command.Type.MOTION, commandFlags, Shortcut(key))
        }
    }
}

