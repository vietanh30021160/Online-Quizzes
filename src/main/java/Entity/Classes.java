package Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Classes")
public class Classes {
    @Id
    @Column(name = "ClassID")
    private Integer classId;

    @Column(name = "TeacherID")
    private Integer teacherId;

    @Column(name = "ClassName")
    private String className;

    public Integer getClassId() {
        return this.classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
