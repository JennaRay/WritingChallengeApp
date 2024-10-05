import java.time.LocalDateTime
import java.util.*

class Book(type: String, name: String) {

    private var name: String = ""
    private var type: String = ""
    private var entries: MutableList<Entry> = mutableListOf()
    private var entryNames: MutableList<String> = mutableListOf()

    init {
        this.name = name
        this.type = type
    }

    fun setType(type: String) {
        this.type = type
    }
    fun open() {
        var open: Boolean = true
        while (open) {
            println("Name: $name \nEntries: ")
            for (entry in entries) {
                println("${entries.indexOf(entry) + 1}. ${entry.getFormattedAsLineItem()}")
            }
            println("1. Open entry \n2. New Entry\n3. Display All Entries\n4. Close Book")
            var choice = readln()
            if (choice == "1") {
                if (entryNames.isNotEmpty()) {
                    println("Type the number of the entry you would like to open: ")
                    var whichEntry = readln()
                    var index = whichEntry.toInt() - 1
                    if (index >= entries.size) {
                        index == entries.size - 1
                    } else if (index < 0) {
                        index = 0
                    }
                    entries[index].display()
                }
                else {println("This book doesn't have any entries yet")}
            } else if (choice == "2") {
                    if (this.type == "prompt") {
                        addEntryWithPrompt()
                    }
                    if (this.type == "sprint"){
                        addTimedEntry()
                    }

                }
            else if (choice == "3")
            {
                this.display()
            }
            else
            {
                open = false
            }
        }
    }

    fun display() {
        println(name)
        for (entry in entries) {
            entry.display()
        }
    }

    fun addEntryWithPrompt() {
        var newEntry = Entry()
        newEntry.setPrompt(getPrompt())
        println(newEntry.getPrompt())
        println("Hit enter twice when you're done with your entry")
        var writing: Boolean = true
        while (writing) {
            newEntry.writeText()
            if (newEntry.getLastParagraph() == "" || newEntry.getLastParagraph() == " ")
            {
                writing = false
            }
        }
        println("\nWhat do you want to name this entry?")
        var name: String = readln().toString()
        newEntry.setName(name)
        this.entries.add(newEntry)
        println("Entry has been added")
        entryNames.add(newEntry.getName())
    }
    fun addTimedEntry() {
        var newEntry = Entry()
        println("How long do you want your writing sprint to be? (answer in whole minutes)")
        var answer = readln()
        var sprintLength: Long = answer.toLong()
        val startTime = LocalDateTime.now()
        val endTime = startTime.plusMinutes(sprintLength)
        print("Ready... Set... WRITE!")
        var writing: Boolean = true
        while (writing)
        {
            newEntry.writeText()
            if (LocalDateTime.now() >= endTime) {
                writing = false
                println("Times up!")
            }
        }
        println("\nWhat do you want to name this entry?")
        var name: String = readln().toString()
        newEntry.setName(name)
        this.entries.add(newEntry)
        println("Entry has been added")
        entryNames.add(newEntry.getName())
    }

    fun getPrompt(): String
    {
        //first ten prompts from https://robinpiree.com/blog/creative-writing-prompts
        var prompts: MutableList<String> = mutableListOf("Imagine a world where music can literally change the weather. Write a story about a character who uses this power to communicate emotions, transforming the skies to reflect their inner turmoil or joy."
            , "Create a tale where the main character wakes up in a different period every morning. Explore how they adapt and learn from these diverse historical settings and their challenges in maintaining their identity.",
            "Write from the perspective of a house that has stood for centuries. Narrate its experiences and the secrets it has seen, focusing on the generations of families living within its walls.",
            "In a future where all digital information has been lost, the last remaining library holds the key to humanity's past. Craft a story about the individuals who guard and seek knowledge in this sacred place.",
            "Invent a world where colors are a finite resource. Follow a character known as the Color Thief, who steals hues from nature to sell on the black market and their eventual realization of the consequences of their actions.",
            "Write a poetic narrative novel about a lonely astronaut who develops a friendship with the Moon, sharing their deepest fears and dreams during their extended mission in space.",
            "Describe a city that is invisible to the outside world. Detail the lives of its inhabitants, their culture, and how they deal with the threat of discovery by the outside world.",
            "A character starts hearing echoes of future events in their dreams. Explore how they use this knowledge to navigate their daily lives and their moral dilemmas.",
            "A single flower blooms in a desolate landscape in a world devoid of flora. Write a story centered around various characters' interactions with this last symbol of nature.",
            "Dive into the life of a clockmaker who creates timepieces that can alter moments in time. Detail the adventures and misadventures when one of these clocks falls into the wrong hands.",
            "Write about a time in your life when you witnessed something strange")
        var index: Int = (1..(prompts.size)).random()
        return prompts[index-1]
    }

    fun save(): String {
        var content: String = ""
        content += this.name + "||"
        content += this.type + "||"
        for (entry in entries) {
            content += entry.save()
        }
        return content
    }

    fun load(entries: String) {
        var parts = entries.split("***")
        println(parts)
        for (part in parts)
        {
            if (part.isNotEmpty()) {
                var entryParts = part.split(">>")
                var title: String = entryParts[0]
                var prompt: String = entryParts[1]
                var date: String = entryParts[2]
                var paragraphs: String = entryParts[3]
                var newEntry = Entry()
                newEntry.setName(title)
                newEntry.setPrompt(prompt)
                newEntry.setDate(date)
                newEntry.load(paragraphs)
                this.entries.add(newEntry)
                this.entryNames.add(newEntry.getName())
            }
        }
    }
}