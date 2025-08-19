# Gehtsoft Technical Assessment

## Project Description
This project is a console-based Java application that implements two main functionalities:

1. **Caesar Cipher Encryption/Decryption**
    - Supports both English (Latin) and Russian (Cyrillic) alphabets.
    - Preserves uppercase and lowercase letters.
    - Non-alphabetic characters remain unchanged.
    - Supports positive and negative shift values.
    - Can read input from the console or from a file.
    - Supports decrypting with a known shift or trying all possible shifts (brute-force).

2. **Arithmetic Expression Evaluator**
    - Parses and evaluates arithmetic expressions.
    - Supports addition (+), subtraction (-), multiplication (*), division (/).
    - Supports parentheses for operation precedence.
    - Handles decimal numbers and negative values.
    - Throws a clear error on division by zero or malformed expressions.

**The project is designed with modularity in mind:**
- `Menu` handles user interaction and flow.
- `CaesarHandler` handles all Caesar Cipher input and output.
- `ExpressionHandler` handles all arithmetic expression input and output.
- `CaesarCipherApp` and `ExpressionEvaluatorApp` implement the core logic.
---

## How to Compile and Run

1. **Clone the repository:**
   ```bash
   git clone <repository_url>
   cd <repository_folder>
2. **Compile:**
   ```bash
   javac -d out src/org/gehtsoft/**/*.java
3. **Run:**
   ```bash
   java -cp out org.gehtsoft.Main

>Note: Make sure your console supports UTF-8 for proper display of Russian characters.

***

## Approach and Assumptions

**Caesar Cipher:**
- Shift values outside the alphabet range and negative are normalized automatically.
- If shift value is not provided for decryption, shifts in a range [0, 32] are printed. **Note:** In English(Latin) there are 26 letters in alphabet, in this case there are 6 repeated possibilities.
- Input can be entered directly or read from a file by prefix with `file:`.

**Expression Evaluator:**
- Uses `BigDecimal` instead of `Double` for precise arithmetic and proper decimal handling.
- The evaluation is implemented using **recursive descent parser**: 
   - separate methods for expressions, terms, factors;
   - correct operator precedence handling (`*`,`/` have higher precedence than `+`, `-`);
   - support for parentheses (expressions inside have the highest precedence) and unary operators (`+`, `-`).
- Basic error handling is implemented: malformed numbers, division by zero, unexpected tokens.
- WhiteSpaces are ignored.

**General:**
- Code is modulized into separate classes for clarity and maintainability.

---

## Examples of usage

**Caesar Cipher**
-
**Encryption:**
>Welcome to Gehtsoft Technical Assessment
> 
>Please choose an option:
>1. Caesar Cipher Encryption
>2. Caesar Cipher Decryption
>3. Arithmetic Expression Evaluation
>4. Exit
>
>  Enter your choice: 1
> 
>   Enter text to encrypt:Hello Привет!
> 
>   Enter shift value (integer): 3
> 
>   Result: Khoor Тулезх!
> 
>   Continue? (y/n):

**Decryption:**

>**With shift:**
> 
>Enter your choice: 2
> 
>Enter text to decrypt:Khoor Тулезх!
>
>Enter shift value (press Enter to try all): 3
>
>Result: Hello Привет!
> 
> **Without shift**
> 
> Enter your choice: 2
> 
>Enter text to decrypt:Khoor Тулезх!
> 
>Enter shift value (press Enter to try all):
> 
>All possible variables:
> 
>For shift =  1, plaintext:Jgnnq Сткджф!
> 
>For shift =  2, plaintext:Ifmmp Рсйгёу!
> 
>For shift =  3, plaintext:Hello Привет!
> 
>For shift =  4, plaintext:Gdkkn Опзбдс!
> 
> ...

**Expression Evaluator**
-
>Welcome to Gehtsoft Technical Assessment
> 
>Please choose an option:
>1. Caesar Cipher Encryption
>2. Caesar Cipher Decryption
>3. Arithmetic Expression Evaluation
>4. Exit
>
>  Enter your choice: 3
> 
>   Enter arithmetic expression: -.5+5/(5 + 5)
> 
>   Result = 0
> 
>   Continue? (y/n):

## License

_This project was implemented as part of Gehtsoft technical assessment_