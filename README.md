# Лабораторна робота №1 — Сучасні можливості Java та підхід Data-Oriented Programming (DOP)

## Опис проєкту

Проєкт реалізує систему управління замовленнями за підходом DOP. Дані та логіка чітко розділені:

- **дані** описані незмінними `record`-ами, що входять до `sealed interface`;
- **логіка** винесена в окремий клас `OrderProcessor`;
- обробка виконується через `switch` expression з pattern matching, **без `default`**: компілятор сам перевіряє, що враховано всі статуси замовлення;
- результат формується за допомогою Text Blocks.

### Використані можливості Java

| Можливість | Де застосовано |
|---|---|
| `record` | `Pending`, `Paid`, `Shipped`, `Cancelled` (незмінні дані) |
| `sealed interface` | `OrderStatus` (закритий список статусів) |
| Compact constructor | валідація даних у record-ах |
| Switch expression + pattern matching | `OrderProcessor.process` |
| Text Blocks (`"""`) | форматування результату |

## Розрахунок варіанта

Залікової книжки немає, тому, згідно із завданням, використано порядковий номер студента у списку групи IM-42.

N = 21

```
V = N mod 3 = 21 mod 3 = 0
```

Отже, виконано **варіант 0** — система управління замовленнями.

## Структура проєкту

```
java-lab-1/
├── pom.xml
├── README.md
└── src/
    ├── main/java/ua/kpi/comsys/dop/
    │   ├── OrderStatus.java
    │   ├── Pending.java
    │   ├── Paid.java
    │   ├── Shipped.java
    │   ├── Cancelled.java
    │   └── OrderProcessor.java
    └── test/java/ua/kpi/comsys/dop/
        ├── PaidTest.java
        ├── ShippedTest.java
        ├── CancelledTest.java
        └── OrderProcessorTest.java
```

Усі класи знаходяться в пакеті `ua.kpi.comsys.dop`.

## Модель даних

```
OrderStatus (sealed interface)
├── Pending()
├── Paid(String paymentId, BigDecimal amount)
├── Shipped(String trackingCode, LocalDate dispatchDate)
└── Cancelled(String reason)
```

### Валідація

Некоректні дані призводять до `IllegalArgumentException` у конструкторі record-а.

| Record | Правила |
|---|---|
| `Pending` | немає (не має полів) |
| `Paid` | `paymentId` не `null` і не порожній/пробільний; `amount` не `null` і `> 0` |
| `Shipped` | `trackingCode` не `null` і не порожній/пробільний; `dispatchDate` не `null` |
| `Cancelled` | `reason` не `null` і не порожній/пробільний |

## Обробник

```java
public class OrderProcessor {
    public String process(OrderStatus status) { ... }
}
```

- Якщо `status == null`, кидає `IllegalArgumentException("Status cannot be null")`.
- Для кожного статусу повертає рядок за таким шаблоном:

**Pending**
```
Order Status: PENDING
Details: Order is currently pending processing.
```

**Paid**
```
Order Status: PAID
Details: Payment ID: PAY-123, Amount: $100.50
```

**Shipped**
```
Order Status: SHIPPED
Details: Tracking Code: TRK-456, Dispatch Date: 2026-10-10
```

**Cancelled**
```
Order Status: CANCELLED
Details: Reason: Out of stock
```

### Приклад використання

```java
OrderProcessor processor = new OrderProcessor();

String result = processor.process(new Paid("PAY-123", new BigDecimal("100.50")));
System.out.print(result);
```

## Вимоги до середовища

- JDK **25**
- Maven **3.9+**

Перевірити версії можна командами:

```bash
java -version
mvn -version
```

## Запуск тестів

Тести написані з використанням **JUnit 6**.

```bash
mvn clean test
```

Тести перевіряють:

- коректне створення кожного record-а;
- усі гілки валідації (`null`, порожній рядок, пробіли, нульові та від'ємні суми);
- усі чотири гілки `switch` у `OrderProcessor`;
- виняток при `null` та текст його повідомлення.

## Покриття коду

Для вимірювання покриття використовується **JaCoCo**. Після запуску `mvn clean test` звіт знаходиться тут:

```
target/site/jacoco/index.html
```

Для пакета `ua.kpi.comsys.dop` покриття становить 100%.

<img width="1084" height="196" alt="image" src="https://github.com/user-attachments/assets/5c07ffce-3bb6-4460-90c1-4d7e528cebbd" />


## Технології

- Java 25
- Maven 3.9.16
- JUnit 6.1.3
- JaCoCo 0.8.15
