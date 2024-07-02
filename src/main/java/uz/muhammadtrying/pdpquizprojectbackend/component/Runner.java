package uz.muhammadtrying.pdpquizprojectbackend.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.muhammadtrying.pdpquizprojectbackend.entity.Module;
import uz.muhammadtrying.pdpquizprojectbackend.entity.*;
import uz.muhammadtrying.pdpquizprojectbackend.entity.enums.DifficultyEnum;
import uz.muhammadtrying.pdpquizprojectbackend.interfaces.*;
import uz.muhammadtrying.pdpquizprojectbackend.repo.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final ModuleService moduleService;
    private final QuestionListService questionListService;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final QuestionService questionService;
    private final OptionService optionService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Override
    public void run(String... args) {
        if (ddlAuto.equals("create")) {
            generateData();
        }
    }

    private void generateData() {

        // 2 categories,
        // 2 modules per category (overall 4),
        // 3 questionLists with statuses EASY, MEDIUM, HARD per module (overall 12),
        // 5 questions per questionList (overall 60),
        // 3 options per question (overall 180)

        generateUsers();

        Category category1 = Category.builder().name("Java").photo("https://static-00.iconduck.com/assets.00/flutter-icon-2048x2048-ufx4idi8.png").build();
        Category category2 = Category.builder().name("Python").build();

        categoryService.save(category1);
        categoryService.save(category2);


        Module module1 = Module.builder().category(category1).name("Module-1").build();
        Module module2 = Module.builder().category(category1).name("Module-2").build();
        Module module3 = Module.builder().category(category2).name("Module-1").build();
        Module module4 = Module.builder().category(category2).name("Module-2").build();

        moduleService.save(module1);
        moduleService.save(module2);
        moduleService.save(module3);
        moduleService.save(module4);


        generateQuestionList(module1, DifficultyEnum.EASY, "Basics of Java",
                "What is Java?",
                "What is JVM?",
                "Explain JDK and JRE.",
                "What is a Class in Java?",
                "What is an Object in Java?");
        generateQuestionList(module1, DifficultyEnum.MEDIUM, "OOP in Java",
                "What is Inheritance in Java?",
                "Explain Polymorphism.",
                "What is Abstraction?",
                "What is Encapsulation?",
                "Explain Method Overloading.");
        generateQuestionList(module1, DifficultyEnum.HARD, "Advanced Java",
                "What is a Thread in Java?",
                "Explain Synchronization.",
                "What is Java Stream API?",
                "What is a Lambda Expression?",
                "What is Java Reflection API?");

        generateQuestionList(module2, DifficultyEnum.EASY, "Java Syntax",
                "What is a Variable in Java?",
                "What is a Data Type?",
                "Explain Conditional Statements.",
                "What are Loops in Java?",
                "What is an Array in Java?");
        generateQuestionList(module2, DifficultyEnum.MEDIUM, "Java Collections",
                "What is a List in Java?",
                "Explain Set Interface.",
                "What is a Map?",
                "What is a Queue?",
                "What is the Collections Framework?");
        generateQuestionList(module2, DifficultyEnum.HARD, "Java Concurrency",
                "What is Concurrency?",
                "Explain Thread Pool.",
                "What is Deadlock?",
                "What is a Semaphore?",
                "Explain Executors Framework.");
        generateQuestionList(module3, DifficultyEnum.EASY, "Introduction to Python",
                "What is Python?",
                "Explain Python Lists.",
                "What is a Tuple?",
                "Explain Python Dictionaries.",
                "What are Python Sets?");
        generateQuestionList(module3, DifficultyEnum.MEDIUM, "Python Data Structures",
                "What is a Python List Comprehension?",
                "Explain Dictionary Comprehension.",
                "What is a Generator?",
                "Explain Iterators in Python.",
                "What is a Lambda Function?");
        generateQuestionList(module3, DifficultyEnum.HARD, "Python Advanced Topics",
                "What is a Decorator in Python?",
                "Explain Metaclasses.",
                "What is the GIL in Python?",
                "What is a Coroutine?",
                "Explain Asyncio.");

        generateQuestionList(module4, DifficultyEnum.EASY, "Python Basics",
                "What are Variables in Python?",
                "What is Python Syntax?",
                "Explain Python Functions.",
                "What is Python Indentation?",
                "Explain Python Comments.");
        generateQuestionList(module4, DifficultyEnum.MEDIUM, "Python Functions",
                "What is a Function in Python?",
                "Explain Default Arguments.",
                "What are *args and **kwargs?",
                "What is a Recursive Function?",
                "Explain Higher-Order Functions.");
        generateQuestionList(module4, DifficultyEnum.HARD, "Python Modules",
                "What is a Python Module?",
                "Explain Importing Modules.",
                "What are Built-in Modules?",
                "What is a Package in Python?",
                "Explain Creating a Module.");

    }

    private void generateQuestionList(Module module, DifficultyEnum difficulty, String name, String... questions) {

        QuestionList questionList = QuestionList.builder()
                .module(module)
                .difficulty(difficulty)
                .name(name)
                .build();
        questionListService.save(questionList);


        for (String questionContent : questions) {
            Question question = Question.builder()
                    .questionContent(questionContent)
                    .questionList(questionList)
                    .seconds(10)
                    .build();
            questionService.save(question);

            List<String> options = List.of("Option 1", "Option 2", "Option 3");

            if (module.getCategory().getName().equals("Java")) {
                options = getJavaOptions(questionContent);
            } else if (module.getCategory().getName().equals("Python")) {
                options = getPythonOptions(questionContent);
            }

            for (int i = 0; i < options.size(); i++) {
                Option option = Option.builder()
                        .optionContent(options.get(i))
                        .isCorrect(i == 0)
                        .question(question)
                        .build();
                optionService.save(option);
            }
        }
    }

    private List<String> getJavaOptions(String questionContent) {
        return switch (questionContent) {
            case "What is Java?" -> List.of("Programming language", "Coffee brand", "Type of snake");
            case "What is JVM?" -> List.of("Java Virtual Machine", "Java Volume Manager", "Java Visual Model");
            case "Explain JDK and JRE." ->
                    List.of("Java Development Kit and Runtime Environment", "Data Kit and Runtime Editor", "Display Kit and Remote Editor");
            case "What is a Class in Java?" -> List.of("Blueprint for objects", "Type of function", "Variable");
            case "What is an Object in Java?" -> List.of("Instance of a class", "Type of method", "Data type");
            case "What is Inheritance in Java?" ->
                    List.of("Mechanism to acquire properties from a parent class", "Way to encapsulate data", "Technique to hide details");
            case "Explain Polymorphism." ->
                    List.of("Ability to take multiple forms", "Technique for single action in different ways", "Both of the above");
            case "What is Abstraction?" ->
                    List.of("Hiding implementation details", "Way to achieve multiple inheritance", "Method of creating abstract classes");
            case "What is Encapsulation?" ->
                    List.of("Wrapping up data and code", "Type of polymorphism", "Method to achieve abstraction");
            case "Explain Method Overloading." ->
                    List.of("Multiple methods with same name but different parameters", "Way to achieve abstraction", "Technique for handling exceptions");
            case "What is a Thread in Java?" ->
                    List.of("Lightweight process", "Part of a program that executes independently", "Both of the above");
            case "Explain Synchronization." ->
                    List.of("Control access of multiple threads to shared resources", "Way to start a thread", "Method to achieve polymorphism");
            case "What is Java Stream API?" ->
                    List.of("Abstract layer in Java 8", "Framework for handling streams", "Both of the above");
            case "What is a Lambda Expression?" ->
                    List.of("Feature in Java 8 for functional programming", "Way to define a new type", "Method to create streams");
            case "What is Java Reflection API?" ->
                    List.of("Inspect and manipulate classes at runtime", "Technique for polymorphism", "Create new objects");
            case "What is a Variable in Java?" ->
                    List.of("Container for data values", "Type of function", "Method to create objects");
            case "What is a Data Type?" ->
                    List.of("Classification of data for compiler", "Way to define a new type", "Method to create objects");
            case "Explain Conditional Statements." ->
                    List.of("Statements based on conditions", "Way to encapsulate data", "Technique to achieve polymorphism");
            case "What are Loops in Java?" ->
                    List.of("Control flow statements for repeating code", "Method to handle exceptions", "Way to achieve inheritance");
            case "What is an Array in Java?" ->
                    List.of("Container for fixed values of single type", "Type of method", "Way to create objects");
            case "What is a List in Java?" ->
                    List.of("Ordered collection", "Unordered collection", "Resizable-array implementation");
            case "Explain Set Interface." ->
                    List.of("Collection without duplicate elements", "Type of method", "Implementation of Collection interface");
            case "What is a Map?" ->
                    List.of("Object that maps keys to values", "Way to start a thread", "Way to create objects");
            case "What is a Queue?" ->
                    List.of("Collection to hold elements prior to processing", "Type of method", "Way to achieve polymorphism");
            case "What is the Collections Framework?" ->
                    List.of("Unified architecture for manipulating collections", "Method to handle exceptions", "Way to create objects");
            case "What is Concurrency?" ->
                    List.of("Running programs or parts in parallel", "Inspect and manipulate classes at runtime", "Way to encapsulate data");
            case "Explain Thread Pool." ->
                    List.of("Collection of threads for multiple tasks", "Technique to achieve polymorphism", "Way to handle exceptions");
            case "What is Deadlock?" ->
                    List.of("Situation where threads are blocked forever", "Way to create objects", "Method to achieve abstraction");
            case "What is a Semaphore?" ->
                    List.of("Synchronization construct for resource access", "Way to encapsulate data", "Method to handle exceptions");
            case "Explain Executors Framework." ->
                    List.of("Framework for asynchronous task execution", "Method to create objects", "Way to achieve inheritance");
            default -> List.of("Option 1", "Option 2", "Option 3");
        };
    }

    private List<String> getPythonOptions(String questionContent) {
        return switch (questionContent) {
            case "What is Python?" -> List.of("Programming language", "Type of snake", "Car model");
            case "Explain Python Lists." ->
                    List.of("Ordered collection of items", "Unordered collection", "Immutable collection");
            case "What is a Tuple?" ->
                    List.of("Ordered and immutable collection", "Unordered and mutable", "Immutable elements");
            case "Explain Python Dictionaries." ->
                    List.of("Collection of key-value pairs", "Implementation of Collection interface", "Way to handle exceptions");
            case "What are Python Sets?" ->
                    List.of("Unordered collection of unique elements", "Method to handle exceptions", "Way to encapsulate data");
            case "What is a Python List Comprehension?" ->
                    List.of("Concise way to create lists", "Technique to achieve polymorphism", "Method to handle exceptions");
            case "Explain Dictionary Comprehension." ->
                    List.of("Concise way to create dictionaries", "Way to encapsulate data", "Technique to achieve polymorphism");
            case "What is a Generator?" ->
                    List.of("Function that returns an iterator", "Type of method", "Way to achieve inheritance");
            case "Explain Iterators in Python." ->
                    List.of("Objects for iteration", "Method to handle exceptions", "Way to create objects");
            case "What is a Lambda Function?" ->
                    List.of("Small anonymous function", "Way to start a thread", "Method to create objects");
            case "What is a Decorator in Python?" ->
                    List.of("Design pattern", "Way to achieve inheritance", "Technique to modify function or class behavior");
            case "Explain Metaclasses." ->
                    List.of("Classes of classes", "Way to encapsulate data", "Method to handle exceptions");
            case "What is the GIL in Python?" ->
                    List.of("Global Interpreter Lock", "General Information Language", "Way to create objects");
            case "What is a Coroutine?" ->
                    List.of("Generalization of subroutines for concurrency", "Method to create objects", "Way to handle exceptions");
            case "Explain Asyncio." ->
                    List.of("Library for concurrent code with async/await syntax", "Way to start a thread", "Method to handle exceptions");
            case "What are Variables in Python?" ->
                    List.of("Containers for data values", "Type of method", "Way to encapsulate data");
            case "What is Python Syntax?" ->
                    List.of("Rules defining program structure", "Way to start a thread", "Technique to achieve polymorphism");
            case "Explain Python Functions." ->
                    List.of("Block of code that runs when called", "Method to handle exceptions", "Method to create objects");
            case "What is Python Indentation?" ->
                    List.of("Python's code block definition", "Technique to achieve polymorphism", "Method to handle exceptions");
            case "Explain Python Comments." ->
                    List.of("Non-executing statements", "Way to encapsulate data", "Method to create objects");
            case "What is a Function in Python?" ->
                    List.of("Block of code that runs when called", "Type of function", "Method to create objects");
            case "Explain Default Arguments." ->
                    List.of("Arguments with default values if not provided", "Way to encapsulate data", "Method to handle exceptions");
            case "What are *args and **kwargs?" ->
                    List.of("Special syntax for variable arguments in functions", "Way to start a thread", "Technique to achieve polymorphism");
            case "What is a Recursive Function?" ->
                    List.of("Function that calls itself", "Method to create objects", "Way to encapsulate data");
            case "Explain Higher-Order Functions." ->
                    List.of("Functions that take or return other functions", "Technique to achieve polymorphism", "Method to handle exceptions");
            case "What is a Python Module?" ->
                    List.of("File with Python definitions and statements", "Object-oriented programming concept", "Way to encapsulate data");
            case "Explain Importing Modules." ->
                    List.of("Making one module's code available in another", "Method to handle exceptions", "Method to create objects");
            case "What are Built-in Modules?" ->
                    List.of("Pre-installed modules in Python", "Type of method", "Way to encapsulate data");
            case "What is a Package in Python?" ->
                    List.of("Structure for Python's module namespace", "Method to handle exceptions", "Method to create objects");
            case "Explain Creating a Module." ->
                    List.of("Writing Python modules", "Technique to achieve polymorphism", "Method to handle exceptions");
            default -> List.of("Option 1", "Option 2", "Option 3");
        };
    }

    private void generateUsers() {
        User user1 = User.builder()
                .firstName("Muhammad")
                .lastName("G'ulomov")
                .email("muhammadtrying@gmail.com")
                .password(passwordEncoder.encode("1"))
                .build();

        User user2 = User.builder()
                .firstName("Farangiz")
                .lastName("Nasriddinova")
                .email("farangizxon2004@gmail.com")
                .password(passwordEncoder.encode("1"))
                .build();

        User user3 = User.builder()
                .firstName("Asliddin")
                .lastName("Musulmanov")
                .email("musulmanovasliddin@gmail.com")
                .password(passwordEncoder.encode("asliddin1234"))
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
}
