package by.rusakovich.discussion.model;

import com.datastax.oss.driver.api.mapper.entity.naming.NameConverter;
import edu.umd.cs.findbugs.annotations.NonNull;

public class DCNameConvention implements NameConverter {

    @NonNull
    @Override
    public String toCassandraName(@NonNull String s) {
        return "tbl_" + s.toLowerCase();
    }
}
