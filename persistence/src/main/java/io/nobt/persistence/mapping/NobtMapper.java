package io.nobt.persistence.mapping;

import io.nobt.core.domain.Expense;
import io.nobt.core.domain.Nobt;
import io.nobt.core.domain.NobtId;
import io.nobt.core.domain.Person;
import io.nobt.persistence.entity.ExpenseEntity;
import io.nobt.persistence.entity.NobtEntity;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class NobtMapper implements DomainModelMapper<NobtEntity, Nobt> {

    private final DomainModelMapper<ExpenseEntity, Expense> expenseMapper;

    public NobtMapper(DomainModelMapper<ExpenseEntity, Expense> expenseMapper) {
        this.expenseMapper = expenseMapper;
    }

    @Override
    public Nobt mapToDomainModel(NobtEntity databaseModel) {

        final Set<Person> explicitParticipants = databaseModel.getExplicitParticipants().stream().map(Person::forName).collect(toSet());

        Nobt nobt = new Nobt(new NobtId(databaseModel.getId()), databaseModel.getName(), explicitParticipants);
        databaseModel.getExpenses().stream().map(expenseMapper::mapToDomainModel).forEach(nobt::addExpense);

        return nobt;
    }

    @Override
    public NobtEntity mapToDatabaseModel(Nobt domainModel) {
        return null;
    }
}
