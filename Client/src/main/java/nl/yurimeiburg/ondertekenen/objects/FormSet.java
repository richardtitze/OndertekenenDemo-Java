package nl.yurimeiburg.ondertekenen.objects;
// TODO Remove?
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class FormSet {
    Map<String, FormSetField> formSetFieldMap;
}
