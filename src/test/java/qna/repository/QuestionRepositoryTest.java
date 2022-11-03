package qna.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.domain.Question;
import qna.domain.QuestionTest;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        questionRepository.deleteAll();
    }

    @DisplayName("질문을 저장 후 확인")
    @Test
    void save() {
        Question question = questionRepository.save(QuestionTest.Q1);

        assertAll(
            () -> assertThat(question.getId()).isNotNull(),
            () -> assertThat(question.getTitle()).isEqualTo(QuestionTest.Q1.getTitle()),
            () -> assertThat(question.getContents()).isEqualTo(QuestionTest.Q1.getContents()),
            () -> assertThat(question.getWriterId()).isEqualTo(QuestionTest.Q1.getWriterId())
        );
    }

    @DisplayName("질문을 저장 후 조회 확인")
    @Test
    void findAll() {
        Question question1 = questionRepository.save(QuestionTest.Q1);
        Question question2 = questionRepository.save(QuestionTest.Q2);

        List<Question> result = questionRepository.findAll();

        assertAll(
            () -> assertThat(result).hasSize(2),
            () -> assertThat(result).contains(question1, question2)
        );
    }

    @DisplayName("질문을 저장 후 수정 확인")
    @Test
    void update() {
        Question question = questionRepository.save(QuestionTest.Q1);
        question.setTitle(QuestionTest.Q2.getTitle());

        Optional<Question> result = questionRepository.findById(question.getId());

        assertAll(
            () -> assertThat(result).isPresent(),
            () -> assertThat(result.get().getTitle()).isEqualTo(question.getTitle())
        );
    }

    @DisplayName("질문 저장 후 삭제 확인")
    @Test
    void remove() {
        Question question = questionRepository.save(QuestionTest.Q1);
        questionRepository.delete(question);

        Optional<Question> result = questionRepository.findById(question.getId());

        assertThat(result).isNotPresent();
    }

    @DisplayName("삭제되지 않은 질문 조회")
    @Test
    void findByDeletedFalse() {
        Question question = questionRepository.save(QuestionTest.Q1);

        List<Question> result = questionRepository.findByDeletedFalse();

        assertAll(
            () -> assertThat(result).hasSize(1),
            () -> assertThat(result).contains(question),
            () -> assertThat(result.get(0)).isEqualTo(question)
        );
    }

    @DisplayName("질문 식별자로 삭제되지 않은 질문 조회")
    @Test
    void findByIdAndDeletedFalse() {
        Question question = questionRepository.save(QuestionTest.Q1);

        Optional<Question> result = questionRepository.findByIdAndDeletedFalse(question.getId());

        assertAll(
            () -> assertThat(result.isPresent()).isTrue(),
            () -> assertThat(result.get()).isEqualTo(question)
        );
    }
}