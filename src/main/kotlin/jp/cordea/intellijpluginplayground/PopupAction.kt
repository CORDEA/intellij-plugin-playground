package jp.cordea.intellijpluginplayground

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class PopupAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showYesNoDialog(
                e.project,
                e.presentation.description,
                e.presentation.text,
                Messages.getOkButton(),
                Messages.getCancelButton(),
                Messages.getInformationIcon()
        )
    }
}
