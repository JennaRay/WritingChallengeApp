import java.io.File

class App {
    private val filename: String = "writingBuddyData.txt"
    private var books: MutableList<Book> = mutableListOf()
    private var bookNames: MutableList<String> = mutableListOf()
    private val menu: List<String> = listOf("1. New Book", "2. Open Book", "3. Exit")

    init {
        val file = File(filename)
        //create a new file
        val isNewFileCreated :Boolean = file.createNewFile()

        if(isNewFileCreated){
            println("Welcome")
        }
        else {
            load()
        }
    }

    fun displayMenu(): Boolean {
        menu.forEach { println(it) }
        println("What would you like to do?")
        var choice = readln()

        if (choice == "1")
        {
            addBook()
            return true
        }
        else if (choice == "2")
        {
            if (this.bookNames.isEmpty()) {
                println("You do not have any books yet.")
                return true
            }
            openBook()
            return true
        }
        else if (choice == "3")
        {
            save()
            println("K bye")
            return false
        }
        else {
            println("That wasn't a valid option. Try again")
            return true
        }
    }

    fun addBook() {
        println("Choose a challenge type (1 or 2)\n")
        println("1. Prompt Challenge:\n Write a short story for each entry based off a given writing prompt\n")
        println("2. Sprint Challenge:\n Choose a project/story idea and each day write a little bit more of it. Timed.\n")
        var typeNum = readln()
        var type: String = ""
        if (typeNum == "2")
        {
            type = "sprint"
        }
        else if (typeNum == "1")
        {
            type = "prompt"

        }
        print("Name the book:")
        var name = readln()
        var newBook = Book(type, name)
        books.add(newBook)
        bookNames.add(name)
        println("Book '$name' has been added to your library")
        println("Would you like to open $name ? (yes/no)")
        var choice = readln()
        if (choice == "yes")
        {
            newBook.open()
        }
        else {return}
    }

    fun openBook() {
        // load and open existing book
        println("Which book would you like to open")
        for (name in bookNames) {
            println("${bookNames.indexOf(name) + 1}.  $name")
        }
        var choice = readln()
        var book: Book = books[choice.toInt() -1]
        book.open()
    }

    fun save() {
        var content: String = ""
        val file = File(this.filename)
        file.writeText(content)
            for (book in books)
            {
                content += book.save()
                file.appendText(content)
            }
        println("--Saved Successfully--")
    }

    fun load() {
        var file = File(this.filename)
        var lines: List<String> = file.bufferedReader().readLines()
        println(lines)
        for (line in lines)
        {
            var parts = line.split("||")
            var name = parts[0]
            var type = parts[1]
            var entries = parts[2]
            var newBook = Book(type, name)
            newBook.load(entries)
            books.add(newBook)
            bookNames.add(name)
        }
    }

}