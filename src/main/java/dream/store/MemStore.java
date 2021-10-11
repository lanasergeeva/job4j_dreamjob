package dream.store;

import dream.model.Candidate;
import dream.model.Post;
import dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final AtomicInteger POSTID = new AtomicInteger(4);
    private static final AtomicInteger CANDID = new AtomicInteger(4);
    private static final AtomicInteger USID = new AtomicInteger(4);
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Необходимо доработать несколько сервисов по товарной "
                + "группе Мех (БЛЛК и Процессинг) Системная аналитика и кураторы по сервисам с нашей стороны будут\n"
                + "• Микросервисы на Java 11 + Kotlin\n"
                + "• Используем Spring Boot 2.2+\n"
                + "• Kubernetes\n"
                + "• Разработка микросервисов для системы маркировки товаров на базе Java\n"
                + "• Работа с шифрованием\n"
                + "• Создание внутренних инструментов\n"
                + "• Работа с высоконгруженной системой и решение проблем производительности и отказоустойчивости\n"
                + "• Работа с БД postgreSql, Hbase\n"
                + "Требуемые навыки\n"
                + "• Опыт работы в коммерческой разработке от 2х лет\n"
                + "• Опыт работы с Java 11, Spring Boot, Spring Core, ProstreSQL, JPA\n"
                + "• Базовые знания работы с СУБД\n"
                + "• Опыт работы с REST API\n"
                + "• Будет преимуществом опыт работы с Kafka, Docker/Kuberneties, КриптоПРО, flyway, Redis"));
        posts.put(2, new Post(2, " Backend-разработчик PHP", "ОБЯЗАННОСТИ:\n"
                + "Разработка и сопровождение web-приложений и сервисов;\n"
                + "\n"
                + "ТРЕБОВАНИЯ:\n"
                + "Уверенное знание PHP 7+, ООП, SOLID;\n"
                + "MySQL, прямые запросы, создание индексов, оптимизация запросов;\n"
                + "Знание и использование основных паттернов проектирования;\n"
                + "Опыт коммерческой разработки на Laravel/Yii от 2-х лет;\n"
                + "Желательно Vue.js/ любой фреймворк для разработки приложений в реактивном стиле;\n"
                + "Опыт работы Git, Gitflow.\n"
                + "УСЛОВИЯ:\n"
                + "Оформление согласно ТК РФ\n"
                + "Вся заработная плата \"белая\", выплаты 2 раза в месяц\n"
                + "График работы 5/2 гибкое начало и завершения дня (возможна удаленная работа в зависимости от Вашего желания)\n"
                + "Бизнес-центр класса А с кондиционерами,вентиляторами и отличной системой обогрева\n"
                + "Собственный крытый паркинг для сотрудников бесплатно\n"
                + "ЗАБОТА О ЗДОРОВЬЕ\n"));
        posts.put(3, new Post(3, "Вакансия QA Engineer", "Ваши будущие задачи:\n"
                + "Осуществление функций входного контроля разработок на соответствии "
                + "технического задания и отсутствие ошибок (анализ функциональных требований)\n"
                + "Проведение тестирования и составление отчетов о ходе тестирования "
                + "(создание планов первичного тестирования и тест-кейсов, создание и проверка багов) "
                + "с целью обеспечения высокого уровня качества выпускаемых программных продуктов\n"
                + "Взаимодействие с разработчиками по принятию решений о приоритетности и ходу решения выявленных дефектов при тестировании\n"
                + "Принятие решений по выявленным техническим проблемам, возникающим в процессе тестирования\n"
                + "Чтобы стать кандидатом, нужно:\n"
                + "Умение работать с инструментами Postman, Swagger\n"
                + "Знание SQL\n"
                + "Опыт написания автотестов на C# - будет плюсом\n"
                + "Опыт написания автотестов на .NET - будет плюсом\n"));
        candidates.put(1, new Candidate(1, "Ivan Volkov", "Java Junior"));
        candidates.put(2, new Candidate(2, "Oleg Belkin", "Middle Java"));
        candidates.put(3, new Candidate(3, "Anna Mosina", "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public Post findByIdPost(int id) {
        return posts.get(id);
    }

    @Override
    public Candidate findByIdCandidate(int id) {
        return candidates.get(id);
    }

    @Override
    public User findByEmailUser(String name) {
        User rsl = null;
        for (User user : users.values()) {
            if (user.getEmail().equals(name)) {
                rsl = user;
                break;
            }
        }
        return rsl;
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void savePost(Post post) {
        if (post.getId() == 0) {
            post.setId(POSTID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }


    @Override
    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(USID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public void deleteCandidate(int id) {
        Candidate can = findByIdCandidate(id);
        if (can != null) {
            candidates.remove(id);
        }
    }

    @Override
    public void deletePost(int id) {
        Post post = findByIdPost(id);
        if (post != null) {
            posts.remove(id);
        }
    }
}
