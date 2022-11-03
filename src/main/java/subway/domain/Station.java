package subway.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.domain.Persistable;

@Entity
public class Station implements Persistable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;

    protected Station() {
    }

    public Station(String name) {
        this(null, name);
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(final String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
        if (!line.getStations().contains(this)) {
            line.getStations().add(this);
        }
    }
}
