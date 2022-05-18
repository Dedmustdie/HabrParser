package core.habr.abstraction;

import lombok.Getter;
import lombok.Setter;


    @Setter
    @Getter
public abstract class ProgressHandler implements ProgressHandlerMethod {
    private int barDivisionCount = 10;
}
