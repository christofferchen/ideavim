/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2020 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.maddyhome.idea.vim;

import com.intellij.openapi.extensions.ExtensionPointListener;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.extensions.PluginDescriptor;
import com.maddyhome.idea.vim.group.KeyGroup;
import com.maddyhome.idea.vim.handler.ActionBeanClass;
import com.maddyhome.idea.vim.handler.EditorActionHandlerBase;
import com.maddyhome.idea.vim.key.MappingOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class RegisterActions {

  public static final ExtensionPointName<ActionBeanClass> VIM_ACTIONS_EP =
    ExtensionPointName.create("IdeaVIM.vimAction");
  private static boolean initialRegistration = false;

  static {
    // IdeaVim doesn't support contribution to VIM_ACTIONS_EP extension point, so technically we can skip this update,
    //   but let's support dynamic plugins in a more classic way and reload actions on every EP change.
    // TODO: [VERSION UPDATE] since 191 use
    //  ExtensionPoint.addExtensionPointListener(ExtensionPointListener<T>, boolean, Disposable)
    // TODO: [VERSION UPDATE] since 201 use
    //  ExtensionPoint.addExtensionPointListener(ExtensionPointChangeListener, boolean, Disposable)
    VIM_ACTIONS_EP.getPoint(null).addExtensionPointListener(new ExtensionPointListener<ActionBeanClass>() {
      @Override
      public void extensionAdded(@NotNull ActionBeanClass extension, @NotNull PluginDescriptor pluginDescriptor) {
        // Suppress listener before the `VimPlugin.turnOn()` function execution. This logic should be rewritten after
        //   version update (or earlier).
        if (!initialRegistration) return;
        unregisterActions();
        registerActions();
      }

      @Override
      public void extensionRemoved(@NotNull ActionBeanClass extension, @NotNull PluginDescriptor pluginDescriptor) {
        if (!initialRegistration) return;
        unregisterActions();
        registerActions();
      }
    });
  }

  /**
   * Register all the key/action mappings for the plugin.
   */
  public static void registerActions() {
    registerVimCommandActions();
    registerEmptyShortcuts();
    initialRegistration = true;
  }

  public static @Nullable EditorActionHandlerBase findAction(@NotNull String id) {
    return VIM_ACTIONS_EP.extensions().filter(vimActionBean -> vimActionBean.getActionId().equals(id)).findFirst()
      .map(ActionBeanClass::getAction).orElse(null);
  }

  public static @NotNull EditorActionHandlerBase findActionOrDie(@NotNull String id) {
    EditorActionHandlerBase action = findAction(id);
    if (action == null) throw new RuntimeException("Action " + id + " is not registered");
    return action;
  }

  public static void unregisterActions() {
    VimPlugin.getKey().unregisterCommandActions();
  }

  private static void registerVimCommandActions() {
    KeyGroup parser = VimPlugin.getKey();
    VIM_ACTIONS_EP.extensions().forEach(parser::registerCommandAction);
  }

  private static void registerEmptyShortcuts() {
    final KeyGroup parser = VimPlugin.getKey();

    // The {char1} <BS> {char2} shortcut is handled directly by KeyHandler#handleKey, so doesn't have an action. But we
    // still need to register the shortcut, to make sure the editor doesn't swallow it.
    parser.registerShortcutWithoutAction(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), MappingOwner.IdeaVim.INSTANCE);
  }
}
