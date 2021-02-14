package kz.edu.nurym.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LogFilter implements Filter {
    @Override
    public boolean isLoggable(LogRecord record) {
        return !record.getSourceClassName().startsWith("kz.edu.nurym.repository.interfaces.ExchangeRateRepo");
    }
}
