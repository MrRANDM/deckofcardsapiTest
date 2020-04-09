import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String message;
    private Integer result;

    @Override
    public String toString() {
        return "TestResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
