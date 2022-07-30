package jp.cordea.intellijpluginplayground

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor

class CodeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = Visitor()
}

private class Visitor : PsiElementVisitor() {
    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
    }
}
