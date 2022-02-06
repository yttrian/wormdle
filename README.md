# Wormdle

The Destiny lore word game, that's definitely not just a worse version of Wordle!

### Why not use an existing clone?

Ever wanted to play Wordle, but hated JavaScript and other useful features?
Why not try implementing the same game, but with HTML forms, scary CSS, and a server you will definitely need to pay
for because it's not just static files. But of course you're joking, and would've been fine using JavaScript and just
wanted to see if you could make a virtual keyboard with CSS alone. (Sorry real keyboard users.)

Also, you just happen to be a fan of Destiny lore ~~and want to prove yourself after The Event that left you traumatized
and with permanent trust issues~~.

### How did you make the word list?

Simple.

1. Pulled a (mostly) complete copy of the content for all entries, cards, items, and records
2. Used NLTK
   1. Used Punkt to tokenize all the words
   2. Filtered to all 5-letter words
   3. Filtered out all stopwords
   4. Used WordNetLemmatizer to filter out plurals
   5. Checked that each word matches `[A-Za-z]{5}`
   6. Calculated the top 200 words from the Brown corpus
   7. Mapped the first occurrence of each word, not in the top 200, with its slug and title
   8. Saved this list
3. Hastily looked over the whole list and tried to remove odd ones (or ones that made me sad), and probably missed a few!

### Any benefits to this approach?

Tons!

- I didn't need to write any JavaScript
- I got to use Kotlin, including for CSS
- You can't just check your Local Storage for today's word
- I get to see if Ktor can handle the potential for popularity

## Attributions

* [Kotlin](https://kotlinlang.org/)
* [Ktor](https://github.com/ktorio/ktor)
* [logback](https://github.com/qos-ch/logback)
* [Turbo](https://github.com/hotwired/turbo)
* [NLTK](https://www.nltk.org/)
* [words (Unix)](https://en.wikipedia.org/wiki/Words_(Unix)) via NLTK
