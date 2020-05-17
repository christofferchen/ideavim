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

package org.jetbrains.plugins.ideavim.extension.exchange

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper
import com.maddyhome.idea.vim.helper.VimBehaviorDiffers
import org.jetbrains.plugins.ideavim.VimTestCase

class VimExchangeExtensionTest : VimTestCase() {
  @Throws(Exception::class)
  override fun setUp() {
    super.setUp()
    enableExtensions("exchange")
  }

  // |cx|
  fun `test exchange words left to right`() {
    doTest(StringHelper.parseKeys("cxe", "w", "cxe"),
      "The quick ${c}brown fox catch over the lazy dog",
      "The quick fox ${c}brown catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |cx|
  fun `test exchange words dot repeat`() {
    doTest(StringHelper.parseKeys("cxiw", "w", "."),
      "The quick ${c}brown fox catch over the lazy dog",
      "The quick fox ${c}brown catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |cx|
  fun `test exchange words right to left`() {
    doTest(StringHelper.parseKeys("cxe", "b", "cxe"),
      "The quick brown ${c}fox catch over the lazy dog",
      "The quick ${c}fox brown catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |cx|
  fun `test exchange words right to left with dot`() {
    doTest(StringHelper.parseKeys("cxe", "b", "."),
      "The quick brown ${c}fox catch over the lazy dog",
      "The quick ${c}fox brown catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |X|
  fun `test visual exchange words left to right`() {
    doTest(StringHelper.parseKeys("veX", "w", "veX"),
      "The quick ${c}brown fox catch over the lazy dog",
      "The quick fox ${c}brown catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |X|
  @VimBehaviorDiffers(
    originalVimAfter = "The ${c}brown catch over the lazy dog",
    shouldBeFixed = true
  )
  fun `test visual exchange words from inside`() {
    doTest(StringHelper.parseKeys("veX", "b", "v3e", "X"),
      "The quick ${c}brown fox catch over the lazy dog",
      "The brow${c}n catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |X|
  @VimBehaviorDiffers(
    originalVimAfter = "The brown ${c}catch over the lazy dog",
    shouldBeFixed = true
  )
  fun `test visual exchange words from outside`() {
    doTest(StringHelper.parseKeys("v3e", "X", "w", "veX"),
      "The ${c}quick brown fox catch over the lazy dog",
      "The brow${c}n catch over the lazy dog",
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |cxx|
  @VimBehaviorDiffers(
    originalVimAfter =
    """The quick
       catch over
       ${c}brown fox
       the lazy dog
       """,
    shouldBeFixed = true
  )
  fun `test exchange lines top down`() {
    doTest(StringHelper.parseKeys("cxx", "j", "cxx"),
      """The quick
         brown ${c}fox
         catch over
         the lazy dog""".trimIndent(),
      """The quick
         ${c}catch over
         brown fox
         the lazy dog""".trimIndent(),
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }

  // |cxx|
  @VimBehaviorDiffers(
    originalVimAfter =
    """The quick
       catch over
       ${c}brown fox
       the lazy dog
       """,
    shouldBeFixed = true
  )
  fun `test exchange lines top down with dot`() {
    doTest(StringHelper.parseKeys("cxx", "j", "."),
      """The quick
         brown ${c}fox
         catch over
         the lazy dog""".trimIndent(),
      """The quick
         ${c}catch over
         brown fox
         the lazy dog""".trimIndent(),
      CommandState.Mode.COMMAND,
      CommandState.SubMode.NONE
    )
  }
}