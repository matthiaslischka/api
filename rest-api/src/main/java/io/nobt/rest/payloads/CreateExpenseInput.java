package io.nobt.rest.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.nobt.core.domain.Person;
import io.nobt.core.domain.Share;
import io.nobt.rest.constraints.CheckNoDuplicateDebtors;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

public class CreateExpenseInput {

    @NotEmpty
    @JsonProperty(value = "name", required = true)
    public String name;

    @Valid
    @JsonProperty(value = "debtee", required = true)
    public Person debtee;

    @Valid
    @JsonProperty(value = "splitStrategy", required = true)
    public String splitStrategy;

    @Valid
    @CheckNoDuplicateDebtors
    @JsonProperty(value = "shares", required = true)
    public List<Share> shares;

    @JsonProperty(value = "date", required = true)
    public LocalDate date;
}
