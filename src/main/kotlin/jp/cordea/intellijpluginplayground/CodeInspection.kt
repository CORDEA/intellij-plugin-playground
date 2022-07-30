package jp.cordea.intellijpluginplayground

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.debugger.sequence.psi.callName
import org.jetbrains.kotlin.idea.refactoring.changeSignature.usages.explicateReceiverOf
import org.jetbrains.kotlin.idea.references.mainReference
import org.jetbrains.kotlin.psi.KtCallExpression
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression

class CodeInspection : LocalInspectionTool() {
    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = Visitor(holder)
}

private class Visitor(private val holder: ProblemsHolder) : PsiElementVisitor() {
    override fun visitElement(element: PsiElement) {
        super.visitElement(element)
        if (element !is KtDotQualifiedExpression || element.receiverExpression.text != "Log") {
            return
        }
        val expression = (element.selectorExpression as? KtCallExpression) ?: return
        val level = expression.callName()
        val args = expression.valueArguments.map { it.name }
        holder.registerProblem(element, "Avoid using Log, consider using Timber instead.")
    }
}
