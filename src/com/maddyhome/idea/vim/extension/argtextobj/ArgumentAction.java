package com.maddyhome.idea.vim.extension.argtextobj;

import com.maddyhome.idea.vim.action.TextObjectAction;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.MappingMode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.Set;

/**
 * ArgumentAction
 *
 * @author Ryan.Zheng@aoliday.com
 * @version 1.0
 * @date 2019/6/24 17:19
 */
public abstract class ArgumentAction extends TextObjectAction {
  @NotNull
  @Override
  public Set<MappingMode> getMappingModes() {
    return null;
  }

  @NotNull
  @Override
  public Set<List<KeyStroke>> getKeyStrokesSet() {
    return null;
  }

  @NotNull
  @Override
  public Command.Type getType() {
    return null;
  }
}
