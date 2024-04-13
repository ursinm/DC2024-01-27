package by.bsuir.publicator.repository.editor;

import by.bsuir.publicator.bean.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EditorRepository extends JpaRepository<Editor, BigInteger> {
}
