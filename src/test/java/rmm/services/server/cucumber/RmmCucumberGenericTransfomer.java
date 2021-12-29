package rmm.services.server.cucumber;

import static java.util.Locale.ENGLISH;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.TableCellByTypeTransformer;
import io.cucumber.datatable.TableEntryByTypeTransformer;

/**
 * The Class RmmCucumberGenericTransfomer generic transformer to convert cucumber tables to java objects
 */
@SuppressWarnings("deprecation")
public class RmmCucumberGenericTransfomer implements TypeRegistryConfigurer {

  @Override
  public Locale locale() {
    return ENGLISH;
  }

  @Override
  public void configureTypeRegistry(TypeRegistry typeRegistry) {
    Transformer transformer = new Transformer();
    typeRegistry.setDefaultDataTableCellTransformer(transformer);
    typeRegistry.setDefaultDataTableEntryTransformer(transformer);
    typeRegistry.setDefaultParameterTransformer(transformer);
  }

  /**
   * The Class Transformer.
   */
  private class Transformer implements ParameterByTypeTransformer, TableEntryByTypeTransformer, TableCellByTypeTransformer {
    
    /** The object mapper. */
    ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public Object transform(String s, Type type) {
      return objectMapper.convertValue(s, objectMapper.constructType(type));
    }

    @Override
    public Object transform(Map<String, String> entryValue, Type toValueType, TableCellByTypeTransformer cellTransformer) throws Throwable {
      return objectMapper.convertValue(entryValue, objectMapper.constructType(toValueType));
    }
  }
}
