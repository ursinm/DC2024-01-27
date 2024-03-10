package by.bsuir.rv.repository.editor;

import by.bsuir.rv.bean.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EditorRepository extends JpaRepository<Editor, BigInteger> {
}
