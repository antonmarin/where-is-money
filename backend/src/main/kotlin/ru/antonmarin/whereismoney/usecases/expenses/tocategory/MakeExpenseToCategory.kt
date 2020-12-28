package ru.antonmarin.whereismoney.usecases.expenses.tocategory

import ru.antonmarin.whereismoney.domain.expenses.ExpenseCategory
import ru.antonmarin.whereismoney.domain.income.Account
import ru.antonmarin.whereismoney.usecases.AccountId
import ru.antonmarin.whereismoney.usecases.ExpenseCategoryId
import java.math.BigDecimal
import javax.money.MonetaryAmount
import javax.money.MonetaryAmountFactory

class MakeExpenseToCategory(
    private val accountRepo: AccountByIdRepository,
    private val categoryRepo: CategoryByIdRepository,
    private val moneyFactory: MonetaryAmountFactory<MonetaryAmount>
) {
    fun expense(from: AccountId, to: ExpenseCategoryId, rawAmount: BigDecimal, currencyCode: String) {
        val account: Account = accountRepo.get(from)
        val category: ExpenseCategory = categoryRepo.get(to)
        val amount: MonetaryAmount = moneyFactory.setCurrency(currencyCode).setNumber(rawAmount).create()
        val result = category.expenseFrom(amount, account)
        accountRepo.save(account)
        categoryRepo.save(category)
//        dispatchEvent(event)
    }
}

interface AccountByIdRepository {
    fun get(accountId: AccountId): Account
    fun save(account: Account)
}

interface CategoryByIdRepository {
    fun get(expenseCategoryId: ExpenseCategoryId): ExpenseCategory
    fun save(account: ExpenseCategory)
}
