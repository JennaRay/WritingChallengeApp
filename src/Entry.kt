import java.util.*

class Entry() {
    private var title: String = "Title"
    private var prompt: String = ""
    private var date: String = java.util.Date().toString()
    private var text: MutableList<String> = mutableListOf()

    fun getFormattedAsLineItem(): String {
        return "$title  ------------  $date"
    }

    fun setName(name: String) {
        this.title = name
    }
    fun getName(): String {
        return this.title
    }

    fun getPrompt(): String {
        return prompt
    }

    fun setPrompt(prompt: String) {
        this.prompt = prompt
    }
    fun setDate(date: String) {
        this.date = date
    }
    fun writeText() {
        var content: String = readln()
        this.text.add(content)
    }
    fun getLastParagraph(): String {
        return text.last()
    }
    fun display() {
        println(getFormattedAsLineItem())
        text.forEach { println(it) }
    }

    fun load(paras: String) {
        var parts = paras.split("^^")
        for (part in parts)
        {
            text.add(part)
        }
    }

    fun save(): String {
        var content: String = ""
        content += this.title + ">>"
        content += this.prompt + ">>"
        content += this.date.toString() + ">>"
        for (par in text)
        {
            content += par + "^^"
        }
        content += "***"
        return content
    }
}