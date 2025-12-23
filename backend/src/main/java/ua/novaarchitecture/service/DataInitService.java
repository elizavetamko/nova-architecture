package ua.novaarchitecture.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.novaarchitecture.entity.AdminUser;
import ua.novaarchitecture.entity.Project;
import ua.novaarchitecture.entity.ServiceItem;
import ua.novaarchitecture.entity.TeamMember;
import ua.novaarchitecture.repository.AdminUserRepository;
import ua.novaarchitecture.repository.ProjectRepository;
import ua.novaarchitecture.repository.ServiceItemRepository;
import ua.novaarchitecture.repository.TeamMemberRepository;

/**
 * Service for initializing sample data on startup.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataInitService implements CommandLineRunner {

    private final ProjectRepository projectRepository;
    private final TeamMemberRepository teamRepository;
    private final ServiceItemRepository serviceRepository;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initAdminUser();
        if (projectRepository.count() == 0) {
            initProjects();
        }
        if (teamRepository.count() == 0) {
            initTeamMembers();
        }
        if (serviceRepository.count() == 0) {
            initServices();
        }
        log.info("Data initialization complete");
    }

    private void initAdminUser() {
        if (!adminUserRepository.existsByUsername("admin")) {
            log.info("Creating default admin user...");
            AdminUser admin = AdminUser.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("Адміністратор")
                    .email("admin@nova-arch.ua")
                    .role("ADMIN")
                    .active(true)
                    .build();
            adminUserRepository.save(admin);
            log.info("Default admin user created: admin / admin123");
        }
    }

    private void initProjects() {
        log.info("Initializing sample projects...");

        projectRepository.save(Project.builder()
                .title("Бізнес-центр \"Horizon\"")
                .description("Сучасний бізнес-центр класу А з панорамним склінням та енергоефективними технологіями.")
                .category("commercial")
                .categoryLabel("Комерційна")
                .year("2024")
                .imagePath("images/portfolio/project-1.jpg")
                .location("Київ")
                .area("45 000 м²")
                .featured(true)
                .active(true)
                .sortOrder(1)
                .build());

        projectRepository.save(Project.builder()
                .title("ЖК \"Unity\"")
                .description("Житловий комплекс преміум-класу з унікальною архітектурою та розвиненою інфраструктурою.")
                .category("residential")
                .categoryLabel("Житлова")
                .year("2024")
                .imagePath("images/portfolio/project-2.jpg")
                .location("Київ")
                .area("120 000 м²")
                .featured(true)
                .active(true)
                .sortOrder(2)
                .build());

        projectRepository.save(Project.builder()
                .title("Культурний центр \"Artspace\"")
                .description("Багатофункціональний культурний простір з виставковими залами та концертним майданчиком.")
                .category("cultural")
                .categoryLabel("Культурна")
                .year("2023")
                .imagePath("images/portfolio/project-3.jpg")
                .location("Львів")
                .area("8 500 м²")
                .featured(true)
                .active(true)
                .sortOrder(3)
                .build());

        projectRepository.save(Project.builder()
                .title("Офіс IT-компанії")
                .description("Сучасний офісний простір з відкритим плануванням та зонами для відпочинку.")
                .category("interior")
                .categoryLabel("Інтер'єр")
                .year("2023")
                .imagePath("images/portfolio/project-4.jpg")
                .location("Київ")
                .area("2 500 м²")
                .featured(false)
                .active(true)
                .sortOrder(4)
                .build());

        projectRepository.save(Project.builder()
                .title("Приватна резиденція \"Villa Nova\"")
                .description("Елітний приватний будинок з мінімалістичним дизайном та панорамними вікнами.")
                .category("residential")
                .categoryLabel("Житлова")
                .year("2023")
                .imagePath("images/portfolio/project-5.jpg")
                .location("Козин")
                .area("850 м²")
                .featured(false)
                .active(true)
                .sortOrder(5)
                .build());

        projectRepository.save(Project.builder()
                .title("ТРЦ \"Метрополіс\"")
                .description("Торгово-розважальний центр з інноваційною архітектурою та екологічними технологіями.")
                .category("commercial")
                .categoryLabel("Комерційна")
                .year("2022")
                .imagePath("images/portfolio/project-6.jpg")
                .location("Одеса")
                .area("75 000 м²")
                .featured(false)
                .active(true)
                .sortOrder(6)
                .build());
    }

    private void initTeamMembers() {
        log.info("Initializing team members...");

        teamRepository.save(TeamMember.builder()
                .name("Олександр Петренко")
                .role("Засновник, головний архітектор")
                .bio("15+ років досвіду в архітектурному проєктуванні. Автор понад 50 реалізованих проєктів.")
                .imagePath("images/team/team-1.jpg")
                .email("o.petrenko@nova-arch.ua")
                .active(true)
                .sortOrder(1)
                .build());

        teamRepository.save(TeamMember.builder()
                .name("Марія Коваленко")
                .role("Директор з дизайну")
                .bio("Експерт з інтер'єрного дизайну та колористики. Лауреат міжнародних конкурсів.")
                .imagePath("images/team/team-2.jpg")
                .email("m.kovalenko@nova-arch.ua")
                .active(true)
                .sortOrder(2)
                .build());

        teamRepository.save(TeamMember.builder()
                .name("Дмитро Шевченко")
                .role("Керівник проєктів")
                .bio("Досвідчений project-менеджер з технічною освітою. Забезпечує вчасну реалізацію проєктів.")
                .imagePath("images/team/team-3.jpg")
                .email("d.shevchenko@nova-arch.ua")
                .active(true)
                .sortOrder(3)
                .build());
    }

    private void initServices() {
        log.info("Initializing services...");

        serviceRepository.save(ServiceItem.builder()
                .title("Архітектурне проєктування")
                .description("Від ескізу до робочої документації. Створюємо унікальні проєкти житлових, комерційних та громадських будівель.")
                .features("[\"Передпроєктні дослідження\", \"Концептуальне проєктування\", \"Ескізний проєкт\", \"Робоча документація\", \"Погодження\"]")
                .imagePath("images/services/architecture.jpg")
                .active(true)
                .sortOrder(1)
                .build());

        serviceRepository.save(ServiceItem.builder()
                .title("Дизайн інтер'єрів")
                .description("Проєктуємо інтер'єри, що поєднують естетику та функціональність. Від концепції до підбору меблів.")
                .features("[\"Дизайн-концепція\", \"Планувальні рішення\", \"Підбір матеріалів\", \"Робочі креслення\", \"Комплектація\"]")
                .imagePath("images/services/interior.jpg")
                .active(true)
                .sortOrder(2)
                .build());

        serviceRepository.save(ServiceItem.builder()
                .title("3D візуалізація")
                .description("Фотореалістичні зображення та анімації майбутніх об'єктів. Побачте проєкт до початку будівництва.")
                .features("[\"Екстер'єрні візуалізації\", \"Інтер'єрні візуалізації\", \"Анімаційні ролики\", \"VR-тури\", \"Генплани\"]")
                .imagePath("images/services/visualization.jpg")
                .active(true)
                .sortOrder(3)
                .build());
    }
}
