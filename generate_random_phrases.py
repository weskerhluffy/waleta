import random
import string
import random
import csv
import os

word_size_max = 10
phrase_size_max = 10
file_size_max = 500000000

def generate_word(size):
    return ''.join(random.choice(string.ascii_lowercase) for x in range(size))

def generate_phrase():
    phrase = ""
    number_of_words = random.randint(5, phrase_size_max)
    for _ in range(number_of_words):
        word_size = random.randint(5, word_size_max)
        phrase += generate_word(word_size) + " "
    return phrase

def generate_phrases():
    line_counter = 0
    cur_size = 0
    with open('/tmp/test.txt', 'w', newline='') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter='|', quoting=csv.QUOTE_NONE, escapechar='\\')
        while(cur_size < file_size_max):
            phrases = []
            for _ in range(50):
                phrases.append(generate_phrase())
#            print("las frases %s"%phrases)
            spamwriter.writerow(phrases)
            line_counter += 1
            cur_size = os.fstat(csvfile.fileno()).st_size
#               print(cur_size)
        csvfile.close()

if __name__ == "__main__":
    generate_phrases()
