package com.maddyhome.idea.vim.extension.argtextobj;

import com.maddyhome.idea.vim.action.VimCommandAction;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.MappingMode;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * VimArgumentCommandAction
 *
 * @author Ryan.Zheng@aoliday.com
 * @version 1.0
 * @date 2019/6/24 17:21
 */
public abstract class VimArgumentCommandAction extends VimCommandAction {
  @NotNull
  protected final Set<List<KeyStroke>> keySet;

  VimArgumentCommandAction(@NotNull String keyString) {
    this.keySet = parseKeysSet(keyString);
  }
  @NotNull
  @Override
  public Set<MappingMode> getMappingModes() {
    return MappingMode.VO;
  }

  @NotNull
  @Override
  public Set<List<KeyStroke>> getKeyStrokesSet() {
    return keySet;
  }

  @NotNull
  @Override
  public Command.Type getType() {
    return Command.Type.MOTION;
  }

  @Override
  public EnumSet<CommandFlags> getFlags() {
    return EnumSet.of(CommandFlags.FLAG_MOT_CHARACTERWISE, CommandFlags.FLAG_MOT_INCLUSIVE, CommandFlags.FLAG_TEXT_BLOCK);
  }
}
