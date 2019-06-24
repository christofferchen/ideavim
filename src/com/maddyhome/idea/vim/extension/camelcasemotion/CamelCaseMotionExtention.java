package com.maddyhome.idea.vim.extension.camelcasemotion;

import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.CommandFlags;
import com.maddyhome.idea.vim.command.MappingMode;
import com.maddyhome.idea.vim.extension.VimNonDisposableExtension;
import com.maddyhome.idea.vim.group.KeyGroup;
import com.maddyhome.idea.vim.key.Shortcut;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

public class CamelCaseMotionExtention extends VimNonDisposableExtension {
  @Override
  protected void initOnce() {
    final KeyGroup keyGroup = VimPlugin.getKey();
    keyGroup.registerAction(MappingMode.NVO, "VimMotionCamelEndLeft", Command.Type.MOTION, EnumSet.of(CommandFlags.FLAG_MOT_INCLUSIVE),
      new Shortcut("\\ge"));
    keyGroup.registerAction(MappingMode.NVO, "VimMotionCamelEndRight", Command.Type.MOTION, EnumSet.of(CommandFlags.FLAG_MOT_INCLUSIVE),
      new Shortcut("\\e"));
    keyGroup.registerAction(MappingMode.NVO, "VimMotionCamelLeft", Command.Type.MOTION, EnumSet.of(CommandFlags.FLAG_MOT_EXCLUSIVE),
      new Shortcut("\\b"));
    keyGroup.registerAction(MappingMode.NVO, "VimMotionCamelRight", Command.Type.MOTION, EnumSet.of(CommandFlags.FLAG_MOT_EXCLUSIVE),
      new Shortcut("\\w"));
    keyGroup.registerAction(MappingMode.VO, "VimMotionInnerCamelCaseWord", Command.Type.MOTION,
      EnumSet.of(CommandFlags.FLAG_MOT_CHARACTERWISE, CommandFlags.FLAG_MOT_INCLUSIVE), new Shortcut("i\\w"));
  }

  @NotNull
  @Override
  public String getName() {
    return "CamelCaseMotion";
  }
}
