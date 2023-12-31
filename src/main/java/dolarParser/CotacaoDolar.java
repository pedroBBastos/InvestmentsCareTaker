package dolarParser;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.DigestUtils;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@ToString
@Builder
@Entity
@Table(name = "cotacao_dolar")
public class CotacaoDolar implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @Column(name = "valor")
    private Float valor;

    @Column(name = "data", columnDefinition = "DATE")
    private Date data;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CotacaoDolar)) {
            return false;
        }
        return DateUtils.isSameDay(this.data, ((CotacaoDolar) obj).data);
    }

    @Override
    public int hashCode() {
        // considerando apenas dia/mes/ano para evitar cotacoes duplicadas na insercao ao banco
        Calendar calendar = DateUtils.toCalendar(data);
        String dateStr = calendar.get(Calendar.DAY_OF_MONTH) + "/"
                + calendar.get(Calendar.MONTH) + "/"
                + calendar.get(Calendar.YEAR);
        return Objects.hash(dateStr);
    }

    @Override
    public int compareTo(Object o) {
        return this.data.compareTo(((CotacaoDolar) o).data);
    }
}
