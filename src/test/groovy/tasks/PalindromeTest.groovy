package tasks

import spock.lang.Specification
import java.sql.*;
import groovy.sql.Sql
import tasks.Palindrome
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors
import java.nio.charset.Charset
import java.util.regex.Pattern


class PalindromeTest extends Specification{
	def 'check palidrome strings (inlined test data)'() {
		given:
		Boolean isPalindrome;

		when:
		isPalindrome=Palindrome.isPalindrome(phrase)

		then:
		isPalindrome == isPalindromeResult

		where:

		phrase | isPalindromeResult
		// Very small/null cases.
		"" | true
		"3" | true
		"a" | true
		"ar" | false
		"rr" | true
		"22" | true
		"23" | false
		// General test cases, some with non-printable characters.
		"Get a word in edgeways" | false
		"The best laid schemes of mice and men" | false
		"Strike while the iron is hot" | false
		"Red herring" | false
		"Head over heels" | false
		"Let bygones be bygones" | false
		"Cart before the horse - Put the" | false
		"On a hiding to nothing" | false
		"I'll be there with bells on" | false
		"Put your back up" | false
		"Doesn't know shit from Shinola" | false
		"Spin doctor" | false
		"Top drawer" | false
		"Go off half-cocked" | false
		"Beast with two backs" | false
		"The course of true love never did run smooth" | false
		"When in Rome, do as the Romans do" | false
		"Don't change horses in midstream" | false
		"Face that launched a thousand ships - The" | false
		"A watched pot never boils" | false
		"Never-never land" | false
		"Tooth and nail" | false
		"Blue-plate special" | false
		"Carbon footprint" | false
		"Thick and thin" | false
		"Stick in the mud" | false
		"Inside out" | false
		"Penny saved is a penny earned - A" | false
		"As bald as a coot" | false
		"Cloud cuckoo land" | false
		"An offer he can't refuse" | false
		"Knock into a cocked hat" | false
		"Horse's mouth - straight from the" | false
		"Alter ego" | false
		"All intents and purposes" | false
		"Toe-curling" | false
		"Toffee-nosed" | false
		"Standing on the shoulders of giants" | false
		"The female of the species is more deadly then the male" | false
		"Milk of human kindness" | false
		"Beam ends - On your" | false
		"M�nage � trois" | false
		"Grasp the nettle" | false
		"Step on no Pets" | true
		"Safe sex" | false
		"Keep your nose to the grindstone" | false
		"The Crapper" | false
		"Harbinger of doom" | false
		"Bed of roses" | false
		"The bitter end" | false
		"Blue blood" | false
		"Much of a muchness" | false
		"Stark, raving mad" | false
		"Make a pig's ear of" | false
		"Graveyard shift" | false
		"The proof of the pudding is in the eating" | false
		"(The) fat of the land" | false
		"(Coming in) on a wing and a prayer" | false
		"Power corrupts; absolute power corrupts absolutely" | false
		"Why does bread always fall butter side down?" | false
		"Tout de suite" | false
		"An arm and a leg" | false
		"By the book" | false
		"Loved-up" | false
		"The tail wagging the dog" | false
		"Without let or hindrance" | false
		"A countenance more in sorrow than in anger" | false
		"In the buff" | false
		"An eye for an eye, a tooth for a tooth" | false
		"Turn the tables" | false
		"Which is which?" | false
		"Twenty three skidoo" | false
		"Is the Pope Catholic?" | false
		"Infra dig" | false
		"Too hot to hoot." | true
		"Elementary my dear Watson" | false
		"Speak of the Devil" | false
		"Absence makes the heart grow fonder" | false
		"Lamb to the slaughter" | false
		"Queer as a nine bob note" | false
		"Make hay while the sun shines" | false
		"You can lead a whore to culture but you can't make her think" | false
		"Cold as any stone" | false
		"Picture-perfect" | false
		"Brass monkey weather" | false
		"Grand slam" | false
		"Frog's hair- as fine as" | false
		"Love that dare not speak its name - The" | false
		"La-la land" | false
		"Far be it from me" | false
		"Laughing-stock" | false
		"Blood is thicker than water" | false
		"Ars longa, vita brevis" | false
		"Friend in need is a friend indeed - A" | false
		"Talk the talk" | false
		"Like billy-o" | false
		"Pennies from heaven" | false
		"Ring down the curtain" | false
		"Homonyms" | false
		"Better half" | false
		"Spring forward, fall back" | false
		"Let them eat cake" | false
		"Drag race" | false
		"Hark, hark! the lark at heaven's gate sings" | false
		"Backroom boy" | false
		"Cold shoulder" | false
		"Over the top" | false
		"Penny pinching" | false
		"Halcyon days" | false
		"Pull yourself up by your bootstraps" | false
		"They couldn't hit an elephant at this distance" | false
		"The buck stops here" | false
		"Go whole hog" | false
		"Carry coals to Newcastle" | false
		"Identity theft" | false
		"A skeleton in the closet" | false
		"Stiff upper lip" | false
		"Like the clappers" | false
		"Straight from the horse's mouth" | false
		"Kiss me Hardy" | false
		"Roasted to a turn" | false
		"Nail your colours to the mast" | false
		"Third degree - The" | false
		"Belle of the ball" | false
		"Year dot - The" | false
		"(In a) pig's eye" | false
		"Finger lickin good" | false
		"Put your nose out of joint" | false
		"Surf and turf" | false
		"Ring-fencing" | false
		"Pull out all the stops" | false
		"Curiosity killed the cat" | false
		"Nasty, brutish and short" | false
		"Rosie Lea" | false
		"On the QT" | false
		"Dash to pieces" | false
		"Hedge your bets" | false
		"Mud - your name is" | false
		"Mondegreens" | false
		"Jack Robinson - Before you can say" | false
		"Bread of life - The" | false
		"Fast and loose" | false
		"As cute as a bug's ear" | false
		"Golden key can open any door - A" | false
		"A sorry sight" | false
		"Fish or cut bait" | false
		"Brummagem screwdriver" | false
		"An Englishman's home is his castle" | false
		"Cute as a bug's ear" | false
		"Jerry built" | false
		"Wolf in sheep's clothing" | false
		"Kilroy was here" | false
		"Feeding frenzy" | false
		"Last but not least" | false
		"Life begins at forty" | false
		"No-brainer" | false
		"Beggars can't be choosers" | false
		"Full tilt" | false
		"Better to have loved and lost than never to have loved at all" | false
		"Crapper - The" | false
		"I will swing for you" | false
		"A rolling stone gathers no moss" | false
		"Stars and garters - My" | false
		"Tomorrow is another day" | false
		"Ethnic cleansing" | false
		"Sea change" | false
		"My cup runneth over" | false
		"Writing is on the wall - The" | false
		"Not worth the candle" | false
		"Devil to pay - The" | false
		"Carbon-copy" | false
		"Play by ear" | false
		"Drop-dead gorgeous" | false
		"Bottom drawer" | false
		"Hard man is good to find - A" | false
		"My cup of tea" | false
		"Like two peas in a pod" | false
		"How do you do?" | false
		"Heads up" | false
		"Dropping like flies" | false
		"A dish fit for the gods" | false
		"Aid and abet" | false
		"Shake a leg" | false
		"(By the) skin of your teeth" | false
		"Asap - As soon as possible" | false
		"I'll go to the foot of our stairs" | false
		"Counting sheep" | false
		"As easy as pie" | false
		"Camera cannot lie - The" | false
		"A woman's place is in the home" | false
		"Yellow-belly" | false
		"All at sea" | false
		"(Beware the) Ides of March" | false
		"Nitty-gritty" | false
		"Alive and kicking" | false
		"Delusions of grandeur" | false
		"To sleep: perchance to dream: ay, there's the rub" | false
		"A man who is his own lawyer has a fool for a client" | false
		"Pigeon-chested" | false
		"Don't count your chickens before they are hatched" | false
		"Mad as a march hare" | false
		"Pass the buck" | false
		"Push the boat out" | false
		"Dead as a doornail" | false
		"A thing of beauty is a joy forever" | false
		"Christmas box - A" | false
		"Evil olive" | true
		"There's no such thing as a free lunch (Tanstaafl)" | false
		"Soap-dodger" | false
		"Rule of thumb" | false
		"As fast as greased lightning" | false
		"Not rocket science" | false
		"Go-faster" | false
		"Up a gum tree" | false
		"Fashion victim" | false
		"The real McCoy" | false
		"Sleep like a top" | false
		"Bee-line - make a bee-line for" | false
		"Praying at the porcelain altar" | false
		"Caught red-handed" | false
		"Full to the gunwales" | false
		"One's heart's content" | false
		"Hanged, drawn and quartered" | false
		"Get off on the wrong foot" | false
		"Rumpy-pumpy" | false
		"Bums on seats" | false
		"Pommy bashing" | false
		"The last straw" | false
		"Booze cruise" | false
		"Four corners of the earth" | false
		"Make my day" | false
		"All that glitters is not gold / All that glisters is not gold" | false
		"Meat and two veg." | false
		"No x in Nixon" | true
		"In an interesting condition" | false
		"Gung ho" | false
		"Easy as pie" | false
		"Man does not live by bread alone" | false
		"Doom and gloom" | false
		"Touch wood" | false
		"Devil and the deep blue sea" | false
		"Mouth-watering" | false
		"If I had my druthers" | false
		"Spruce-up" | false
		"Belt up" | false
		"Necessity is the mother of invention" | false
		"Ill wind" | false
		"All in all" | false
		"Sleep tight" | false
		"Fits to a tee" | false
		"A riddle wrapped up in an enigma" | false
		"Shot in the dark" | false
		"The hairy eyeball" | false
		"Got my mojo working" | false
		"Squeaky bum time" | false
		"A miss is as good as a mile" | false
		"Stranger than fiction - Truth is" | false
		"I have nothing to declare but my genius" | false
		"It never rains but it pours" | false
		"Spelling-bee" | false
		"Red-handed (caught)" | false
		"Eat my hat" | false
		"All you can eat" | false
		"Not tonight Josephine" | false
		"Chalk and cheese" | false
		"Sealed with a loving kiss" | false
		"Good as gold" | false
		"Quid pro quo" | false
		"Daniel come to judgement" | false
		"No laughing matter" | false
		"Date rape" | false
		"Thumbs up" | false
		"Elvis has left the building" | false
		"Miss is as good as a mile" | false
		"For the birds" | false
		"Out of sight, out of mind" | false
		"Cock-up" | false
		"Mad as a hatter" | false
		"He will give the Devil his due" | false
		"Salt of the earth - The" | false
		"Love is blind" | false
		"Pleased as Punch" | false
		"Four by two" | false
		"Beat the living daylights out of someone" | false
		"Someone is walking over my grave" | false
		"POSH - Port out, starboard home" | false
		"Boxing Day" | false
		"Sledgehammer to crack a nut - A" | false
		"Square meal" | false
		"Verbosity leads to unclear, inarticulate things" | false
		"Hocus pocus" | false
		"Stuff and nonsense" | false
		"We are a grandmother" | false
		"Give a wide berth" | false
		"As cool as a cucumber" | false
		"Swing the lead" | false
		"How are the mighty fallen?" | false
		"A diamond is forever" | false
		"Get one's dander up" | false
		"Two cents worth" | false
		"Rough diamond" | false
		"All singing, all dancing" | false
		"Worse for wear" | false
		"Cold turkey" | false
		"Hit the hay" | false
		"Katy bar the door" | false
		"(The pen is) mightier than the sword" | false
		"The call of the wild" | false
		"Cut and run" | false
		"In the nick of time" | false
		"Road apples" | false
		"Nothing succeeds like success" | false
		"By the board" | false
		"The quick and the dead" | false
		"As fit as a butcher's dog" | false
		"Down the tubes" | false
		"You can't get blood out of a stone" | false
		"Bean counter" | false
		"Blood and thunder" | false
		"Draw your horns in" | false
		"Not worth a plugged nickel" | false
		"Be still, my beating heart" | false
		"On the shoulders of giants" | false
		"Pull up if I pull up." | true
		"To gild refined gold, to paint the lily" | false
		"In your face" | false
		"Hard and fast" | false
		"Foam at the mouth" | false
		"Trick or treat" | false
		"First World" | false
		"Innocent until proven guilty" | false
		"The bane of my life" | false
		"Pigs might fly" | false
		"Rob Peter to pay Paul" | false
		"Davy Jones' locker" | false
		"Dark horse" | false
		"Quotations" | false
		"Butter wouldn't melt in his mouth" | false
		"Flesh and blood" | false
		"Devil Incarnate - The" | false
		"Hard cases make bad law" | false
		"Charmed life" | false
		"We few, we happy few, we band of brothers" | false
		"Gordon Bennett" | false
		"Whet your appetite" | false
		"Thinking cap" | false
		"Chop and change" | false
		"Pour oil on troubled waters" | false
		"Get on my wick" | false
		"Horse, a horse, my kingdom for a horse - A" | false
		"Blood, sweat and tears" | false
		"Wax poetic" | false
		"Peeping Tom" | false
		"Load of codswallop" | false
		"Going to hell in a handbasket" | false
		"Fair play" | false
		"Strait and narrow" | false
		"Mind your Ps and Qs" | false
		"Dennis, Nell, Edna, Leon, Nedra, Anita, Rolf, Nora, Alice, Carol, Leo, Jane, Reed, Dena, Dale, Basil, Rae, Penny, Lana, Dave, Denny, Lena, Ida, Bernadette, Ben, Ray, Lila, Nina, Jo, Ira, Mara, Sara, Mario, Jan, Ina, Lily, Arne, Bette, Dan, Reba, Diane, Lynn, Ed, Eva, Dana, Lynne, Pearl, Isabel, Ada, Ned, Dee, Rena, Joel, Lora, Cecil, Aaron, Flora, Tina, Arden, Noel, and Ellen sinned." | true
		"Let a thousand flowers bloom" | false
		"One swallow doesn't make a summer" | false
		"Never give a sucker an even break" | false
		"Hard lines" | false
		"Hard hearted" | false
		"Across the board" | false
		"Something old, something new, something borrowed, something blue" | false
		"I man am Regal, a German am I" | true
		"Agree to disagree" | false
		"One sandwich short of a picnic" | false
		"Keep at bay" | false
		"Bandy words" | false
		"Away with the fairies" | false
		"Face the music" | false
		"Market forces" | false
		"(The) order of the boot" | false
		"Comes to the crunch - (When it)" | false
		"Power dressing" | false
		"Beggar belief" | false
		"Red tape" | false
		"End of story" | false
		"Fine as frog's hair" | false
		"All the tea in China - Not for" | false
		"Doc, note, I Dissent. A fast never prevents a fatness. I diet on cod." | true
		"Take a back seat" | false
		"Using a sledgehammer to crack a nut" | false
		"Mumbo jumbo" | false
		"High horse - get off your" | false
		"Touch with a barge-pole - Wouldn't" | false
		"Beat swords into ploughshares" | false
		"From strength to strength" | false
		"Women and children first" | false
		"Trip the light fantastic" | false
		"Pretty kettle of fish" | false
		"Baptism of fire" | false
		"Dreams of empire" | false
		"Pop goes the weasel" | false
		"Gridlock" | false
		"Fight fire with fire" | false
		"A sledgehammer to crack a nut" | false
		"Keep a stiff upper lip" | false
		"(It) fell off the back of a truck" | false
		"Can't hold a candle to" | false
		"Big cheese - The" | false
		"Offing - In the" | false
		"A priori" | false
		"It's all grist to the mill" | false
		"Fancy free" | false
		"Three sheets to the wind" | false
		"Brown as a berry" | false
		"Ship shape and Bristol fashion" | false
		"All of a sudden" | false
		"Hat trick" | false
		"Ducks and drakes" | false
		"Riley - the life of" | false
		"Rack your brains" | false
		"The life of Riley" | false
		"Steal my thunder" | false
		"Chickens come home to roost" | false
		"In the twinkling of an eye" | false
		"Walk free" | false
		"In limbo" | false
		"All present and correct" | false
		"Devil take the hindmost - The" | false
		"The year dot" | false
		"What's in a name? That which we call a rose by any other name would smell as sweet" | false
		"Neither fish nor flesh, nor good red herring" | false
		"Wouldn't touch with a barge-pole" | false
		"Bale out/bail out" | false
		"Bog standard" | false
		"Freeze the balls off a brass monkey - cold enough to" | false
		"Bun in the oven" | false
		"An ill wind" | false
		"Wave a red rag to a bull" | false
		"Scarper" | false
		"Rats live on no evil star." | true
		"Come clean" | false
		"Proverbs - A list of" | false
		"Packed to the gunwales" | false
		"The floozie in the jacuzzi" | false
		"Cut off without a penny" | false
		"Clue - don't have a" | false
		"Kick your heels" | false
		"You are what you eat" | false
		"That's all folks!" | false
		"Tempest in a teapot" | false
		"Pa's a sap" | true
		"To big for your breeches" | false
		"As alike as two peas in a pod" | false
		"Houston, we have a problem" | false
		"Silver lining - every cloud has a" | false
		"Many are called but few are chosen" | false
		"It's better to light a candle than curse the darkness" | false
		"Ten animals I slam in a net" | true
		"Bane of your life" | false
		"Dressed to the nines" | false
		"Down in the dumps" | false
		"Moot point" | false
		"Keep up with the Joneses" | false
		"Van surfing" | false
		"San fairy Ann" | false
		"Been there, done that" | false
		"Coin a phrase" | false
		"Glass ceiling" | false
		"Whole nine yards - The" | false
		"The Devil to pay" | false
		"How now brown cow?" | false
		"There's one (a sucker) born every minute" | false
		"Once in a blue moon" | false
		"Make haste" | false
		"Fall on your sword" | false
		"Shit end of the stick - The" | false
		"Booby prize" | false
		"Rose is a rose is a rose" | false
		"Play ducks and drakes" | false
		"Accidents will happen" | false
		"Absent without leave" | false
		"As queer as a nine bob note" | false
		"Turn up for the books" | false
		"Shall I compare thee to a summer's day?" | false
		"Big Easy - The" | false
		"Urban myth" | false
		"Quicker than lager turns to piss" | false
		"Freezing temperatures" | false
		"To boot" | false
		"Call a spade a spade" | false
		"Bell the cat" | false
		"Do nine men interpret? Nine men, I nod." | true
		"Upper crust" | false
		"Fait accompli" | false
		"As X as Y" | false
		"Word for word" | false
		"Save face" | false
		"Take potluck" | false
		"Whipper snapper" | false
		"Cut of your jib - The" | false
		"Lo and behold" | false
		"A load of codswallop" | false
		"It's that man again" | false
		"The die has been cast" | false
		"Road less travelled - The" | false
		"Harp on" | false
		"Train surfing" | false
		"In the catbird seat" | false
		"Jimmy Horner" | false
		"Bone dry" | false
		"Cut off your nose to spite your face" | false
		"Grinning like a Cheshire cat" | false
		"Exceedingly well read" | false
		"Run the gauntlet" | false
		"He who can, does; he who cannot, teaches" | false
		"Penny dreadful" | false
		"Shambles" | false
		"Don't let the bastards grind you down" | false
		"Compassion fatigue" | false
		"Economical with the truth" | false
		"Wet behind the ears" | false
		"Catbird seat - In the" | false
		"Merry Christmas" | false
		"Uneasy lies the head that wears a crown" | false
		"How sharper than a serpent's tooth it is to have a thankless child" | false
		"Common sense" | false
		"The Big Apple" | false
		"Cheek by jowl" | false
		"Chaise lounge" | false
		"Lonely Tylenol" | true
		"Woman's place is in the home - A" | false
		"Thin air - Vanish into" | false
		"Oops-a-daisy" | false
		"There's an R in the month" | false
		"Faint-hearted" | false
		"Put a damper on" | false
		"Boogie-woogie" | false
		"Ladies' room" | false
		"Cleft stick - In a" | false
		"At loggerheads" | false
		"Back-seat driver" | false
		"Sent to Coventry" | false
		"Night owl" | false
		"Big wig" | false
		"Age before beauty" | false
		"Oh, my stars and garters" | false
		"All fingers and thumbs" | false
		"The ants are my friends, they're blowing in the wind" | false
		"Labour of love" | false
		"Nation of shopkeepers - A" | false
		"Fool's paradise - A" | false
		"Binge drinking" | false
		"White as snow" | false
		"Have your guts for garters" | false
		"Ear-mark" | false
		"Spend a penny" | false
		"As nice as ninepence" | false
		"Punch above one's weight" | false
		"Rinky-dink" | false
		"To a T" | false
		"Get your goat" | false
		"I see you stand like greyhounds in the slips" | false
		"A rose is a rose is a rose" | false
		"On the dole" | false
		"A rose by any other name would smell as sweet" | false
		"Stand up guy" | false
		"It came like a bolt from the blue" | false
		"Life's too short" | false
		"Romeo, Romeo, wherefore art thou Romeo?" | false
		"The exception which proves the rule" | false
		"Chock-a-block" | false
		"Happy as Larry" | false
		"If you can't stand the heat, get out of the kitchen" | false
		"Rhyming slang" | false
		"Norange - A" | false
		"Do geese see god?" | true
		"Hot-blooded" | false
		"Too much of a good thing" | false
		"(Take with a) pinch of salt" | false
		"Double cross" | false
		"Read the riot act" | false
		"Charley horse" | false
		"Misquotes" | false
		"Take care of the pence and the pounds will take care of themselves" | false
		"Black-on-black" | false
		"Have an axe to grind" | false
		"Bitter end - The" | false
		"Get the pip" | false
		"Broad in the beam" | false
		"Join the colours" | false
		"Neither a borrower nor a lender be" | false
		"Game of two halves" | false
		"Bread always falls buttered side down" | false
		"So sue me" | false
		"Chip off the old block" | false
		"Cash on the nail" | false
		"Third time lucky" | false
		"Old chestnut" | false
		"As good as gold" | false
		"The sky's the limit" | false
		"Lick into shape" | false
		"Bullet - Bit the, Bite the" | false
		"Fathom out" | false
		"Winter drawers on" | false
		"For all intents and purposes" | false
		"Diamond is forever - A" | false
		"Fixer-upper" | false
		"Knock back" | false
		"Nod - The land of" | false
		"A hiding to nothing - On" | false
		"Latin Phrases" | false
		"Salad days" | false
		"As mad as a March hare" | false
		"Raining cats and dogs" | false
		"Yellow Peril" | false
		"An albatross around one's neck" | false
		"Purple patch" | false
		"Have no truck with" | false
		"Oxo cube" | false
		"Dog's dinner" | false
		"Friends, Romans, Countrymen, lend me your ears" | false
		"(The) penny drops" | false
		"Piss and vinegar - Full of" | false
		"Toodle-oo" | false
		"Never odd or even" | true
		"Scott (Sir Walter - phrases coined by)" | false
		"Jack the lad" | false
		"On your beam ends" | false
		"Paddle your own canoe" | false
		"A ministering angel shall my sister be" | false
		"Clear blue water" | false
		"Big Apple - The" | false
		"Rose by any other name would smell as sweet - A" | false
		"Dammit I'm mad" | true
		"My mind's eye" | false
		"Show your mettle" | false
		"Prick up your ears" | false
		"Wrong end of the stick - The" | false
		"Bob's your uncle" | false
		"Like being savaged by a dead sheep" | false
		"Keep your powder dry" | false
		"An axe to grind" | false
		"Gussied-up" | false
		"A drop in the bucket" | false
		"Gloom and doom" | false
		"Back of beyond - The" | false
		"Sir Walter Scott (phrases coined by)" | false
		"Big fish in a small pond - A" | false
		"A fate worse than death" | false
		"Tickle the ivories" | false
		"Point Percy at the porcelain" | false
		"Beyond the pale" | false
		"Let's roll" | false
		"Forgive them for they know not what they do" | false
		"Level playing field" | false
		"Fobbed off" | false
		"Deus ex machina" | false
		"Swan song" | false
		"Off with his head" | false
		"Ah, Satan sees Natasha" | true
		"Long in the tooth" | false
		"Under the thumb" | false
		"Drop in the bucket - A" | false
		"Heebie-jeebies - The" | false
		"Dicky-bird - Not a" | false
		"Hanky-panky" | false
		"Drink like a fish" | false
		"On the ball" | false
		"Parting shot / Parthian shot" | false
		"Good in parts" | false
		"A red rag to a bull" | false
		"It's not rocket science" | false
		"To the nth degree" | false
		"Game is afoot - The" | false
		"Shot across the bows" | false
		"Differently abled" | false
		"Comparisons are odious" | false
		"Blaze a trail" | false
		"Stiffen the sinews" | false
		"Let there be light" | false
		"Fuddy-duddy" | false
		"Complete shambles" | false
		"All agog" | false
		"Pull the wool over your eyes" | false
		"Local derby" | false
		"One-hit wonder" | false
		"Sorry sight" | false
		"Khyber pass" | false
		"Toe the line" | false
		"Walter Scott (phrases coined by)" | false
		"Loaf of bread" | false
		"Odds bodkins" | false
		"Guts for garters" | false
		"Birds of a feather flock together" | false
		"Dennis and Edna sinned." | true
		"Bird in the hand is worth two in the bush - A" | false
		"You've never had it so good" | false
		"Clod-hopper" | false
		"Arm candy - see ear candy" | false
		"Twelve good men and true" | false
		"Call of the wild - The" | false
		"Browned off" | false
		"Sleep on a clothesline" | false
		"Pearls before swine" | false
		"Pin money" | false
		"Plug ugly" | false
		"Think outside the box" | false
		"As fit as a fiddle" | false
		"Bet your bottom dollar" | false
		"Sixes and sevens - At" | false
		"In my minds eye" | false
		"Savoir faire" | false
		"Up the pole" | false
		"Fits and starts" | false
		"Road rage" | false
		"Piggyback" | false
		"Ginger up" | false
		"Lion oil." | true
		"Take potluck" | false
		"Willy nilly" | false
		"Tower of strength" | false
		"Bury your head in the sand" | false
		"Middle for diddle" | false
		"Dock your pay" | false
		"I have not slept one wink" | false
		"Top dog" | false
		"(Take with a) grain of salt" | false
		"Caught by the short hairs" | false
		"Dollars to donuts" | false
		"Lisa Bonet ate no basil" | true
		"Down at heel" | false
		"Cheap at half the price" | false
		"Hunt and peck" | false
		"Put the cart before the horse" | false
		"Push the envelope" | false
		"Panic stations" | false
		"A nation of shopkeepers" | false
		"Mal de mer" | false
		"In like Flynn" | false
		"Going for a burton" | false
		"A legend in one's own lifetime" | false
		"Dance attendance on" | false
		"As happy as a sandboy" | false
		"To beggar belief" | false
		"Tickled pink" | false
		"Giddy goat" | false
		"Plates of meat" | false
		"Lead-pipe cinch" | false
		"Biblical phrases" | false
		"Brush - As daft as a" | false
		"Broke - if it ain't, don't fix it" | false
		"Nod is as good as a wink - A" | false
		"Air kiss" | false
		"Montezuma's Revenge" | false
		"Brand spanking new" | false
		"Piece of the action" | false
		"Jack - phrases that include the name Jack" | false
		"Not a dicky-bird" | false
		"The writing is on the wall" | false
		"Chinless wonder" | false
		"Knuckle under" | false
		"Eighty six" | false
		"Jury is still out" | false
		"Leopard cannot change its spots - A" | false
		"Cotton-picking" | false
		"Duvet day" | false
		"Take the gilt off the gingerbread" | false
		"Bunch of fives - A" | false
		"Champ at the bit" | false
		"Chow down" | false
		"Billy-o - Like" | false
		"Fast asleep" | false
		"Paper tiger" | false
		"Ring the changes" | false
		"In the red" | false
		"Sex and shopping" | false
		"Carpe diem" | false
		"Exception that proves the rule - The" | false
		"The balance of trade" | false
		"Driving while black" | false
		"Space, the final frontier" | false
		"Come up trumps" | false
		"Fellow traveller" | false
		"Between the Devil and the deep blue sea" | false
		"The elephant in the room" | false
		"Strain at the leash" | false
		"Codswallop - a load of" | false
		"Strait-laced" | false
		"Funny farm" | false
		"Bad egg" | false
		"Dead as a dodo" | false
		"Hair of the dog that bit you" | false
		"A dog is a man's best friend" | false
		"Sacred cow" | false
		"Fly by the seat of one's pants" | false
		"Cast the first stone" | false
		"Special relationship" | false
		"Fiddling while Rome burns" | false
		"Before you can say Jack Robinson" | false
		"Jam tomorrow" | false
		"No way, Jose" | false
		"Wear your heart on your sleeve" | false
		"Cobblers - A load of" | false
		"Stranger danger" | false
		"Put a sock in it" | false
		"Moaning Minnie" | false
		"Buck stops here - The" | false
		"Left in the lurch" | false
		"He who laughs last laughs longest" | false
		"Het up" | false
		"The fat of the land" | false
		"Say cheese" | false
		"Act of God" | false
		"A picture is worth a thousand words" | false
		"Jack in the box" | false
		"(The) sky's the limit" | false
		"No man is an island" | false
		"Flotsam and jetsam" | false
		"Good measure - For" | false
		"Open season" | false
		"Raze to the ground" | false
		"Survival of the fittest" | false
		"Silver bullet" | false
		"Now I see bees I won" | true
		"In the box-seat" | false
		"High five" | false
		"An apple a day keeps the doctor away" | false
		"On with the motley" | false
		"Blind leading the blind - The" | false
		"Short end of the stick - The" | false
		"About turn" | false
		"Box and Cox" | false
		"Better late than never" | false
		"Vice versa" | false
		"Whole-hearted" | false
		"Wonton? Not now." | true
		"The early bird catches the worm" | false
		"(On a) wing and a prayer" | false
		"Amber nectar" | false
		"Okey-dokey" | false
		"Shaggy dog story" | false
		"A butt tuba" | true
		"The mutt's nuts" | false
		"In trouble" | false
		"Nip and tuck" | false
		"Meat and drink" | false
		"Pass over to the other side" | false
		"Physician heal thyself" | false
		"I'll have your guts for garters" | false
		"Bricks and clicks" | false
		"Keep your hands clean" | false
		"Sick puppy (A)" | false
		"Haven't got a clue" | false
		"Live evil." | true
		"Bygones be bygones - Let" | false
		"Hold your horses" | false
		"Blood toil tears and sweat" | false
		"Keen as mustard" | false
		"Batten down the hatches" | false
		"Wild and woolly" | false
		"Fools' gold" | false
		"In stitches" | false
		"Land of nod - The" | false
		"On the pig's back" | false
		"Beck and call" | false
		"One over the eight" | false
		"Donkey's years" | false
		"Behind the eight ball" | false
		"Full Monty - The" | false
		"Eaten out of house and home" | false
		"The moving finger writes" | false
		"Body - phrases related to the human body" | false
		"Mickey Finn" | false
		"On a clover, if alive, erupts a vast pure evil; a fire volcano" | true
		"Crack of doom - The" | false
		"Birds and the bees - The" | false
		"The opera ain't over till the fat lady sings" | false
		"Bald as a coot" | false
		"Put a spanner in the works" | false
		"As different as chalk and cheese" | false
		"Kiss and tell" | false
		"Done to a turn" | false
		"Cock and bull story" | false
		"Like a moth to a flame" | false
		"Go to the foot of our stairs" | false
		"Richard the Third" | false
		"Nothing is certain but death and taxes" | false
		"Music has charms to soothe the savage breast" | false
		"The law is an ass" | false
		"Fit as a fiddle" | false
		"Black sheep of the family" | false
		"Shebang - The whole" | false
		"Absolute power corrupts absolutely" | false
		"Yo banana boy." | true
		"Wooden hill to Bedfordshire" | false
		"Taken aback" | false
		"Fly on the wall" | false
		"Go pound sand" | false
		"Fish out of water - A" | false
		"Just in time" | false
		"Bugger Bognor!" | false
		"Up a blind alley" | false
		"The customer is always right" | false
		"Make a virtue of necessity" | false
		"A knight in shining armour" | false
		"Shut your cake-hole" | false
		"Oh no! Don Ho!" | true
		"Monty - The full" | false
		"Bottom-up" | false
		"As happy as a clam" | false
		"Start from scratch" | false
		"Word association football" | false
		"Greased lightning" | false
		"Giddy aunt" | false
		"Far from the madding crowd" | false
		"Hit the ground running" | false
		"Chop-chop" | false
		"Tit for tat" | false
		"Go by the book" | false
		"Done a runner" | false
		"Lock stock and barrel" | false
		"On a wing and a prayer" | false
		"Arms akimbo" | false
		"Go hang a salami, I'm a lasagna hog" | true
		"A man, a plan, a canal: Panama." | true
		"Happy as a clam" | false
		"For keeps" | false
		"Fingers and thumbs" | false
		"Different kettle of fish" | false
		"Pony and trap" | false
		"What part of no don't you understand?" | false
		"Bag and baggage" | false
		"Surfing the Net/surfing the Internet/surfing the Web" | false
		"Bode well" | false
		"Play it again Sam" | false
		"Cost an arm and a leg" | false
		"You'll wonder where the yellow went when you brush your teeth with Pepsodent" | false
		"True blue" | false
		"� la carte" | false
		"Jack of all trades, master of none" | false
		"Okay" | false
		"Truth will out" | false
		"Cock-sure" | false
		"The living daylights" | false
		"Draw a blank" | false
		"Weakest link - A chain is only as strong as its" | false
		"O Geronimo, no minor ego" | true
		"For ever and a day" | false
		"Know your onions" | false
		"� la mode" | false
		"Season of mists and mellow fruitfulness" | false
		"Cold comfort" | false
		"I have nothing to offer but blood toil tears and sweat" | false
		"God�s dog." | true
		"Baby father" | false
		"Movers and shakers" | false
		"Cat out of the bag - Let the" | false
		"Don't die like I did" | false
		"Let not poor Nelly starve" | false
		"Sloane Ranger" | false
		"Mexican wave" | false
		"Minced oaths" | false
		"Overpaid, oversexed, and over here" | false
		"Shiver my timbers" | false
		"Loose cannon" | false
		"Beware of Greeks bearing gifts" | false
		"Fine words butter no parsnips" | false
		"Go to pot" | false
		"Walk the walk" | false
		"Black as Newgate's knocker" | false
		"Pick 'n' mix" | false
		"Your money or your life" | false
		"Half inch" | false
		"As daft as a brush" | false
		"Fish rot from the head down" | false
		"Take the bit between your teeth" | false
		"Battle royal" | false
		"Hands down" | false
		"Get - underway" | false
		"Sandwich short of a picnic" | false
		"Down the pan" | false
		"Bad hair day" | false
		"As black as Newgate's knocker" | false
		"No room to swing a cat" | false
		"Good Samaritan" | false
		"Mea culpa" | false
		"Draw, o coward!" | true
		"Left hand doesn't know what the right hand is doing" | false
		"Religion is the opium of the people" | false
		"Get underway" | false
		"A tinker's damn" | false
		"What's not to like?" | false
		"Between two stools" | false
		"You can't teach an old dog new tricks" | false
		"Dark side - The" | false
	}
	def 'check palidrome strings (file test data)'() {
		given:
		Boolean failures = false;
		// This test file contains a couple of thousands of test cases.
		// Every line from the file has a string and a "true"/"false"
		// string which indicates whether we know the string is a
		// palindrome or not.
		String fileName = "palindromes_test.txt"
		List<String> list;

		when:
		if(Files.notExists(Paths.get(fileName))) {
			throw new Exception();
		}

		try  {
			BufferedReader br = new BufferedReader(new FileReader(fileName))

			String line;
			while ((line = br.readLine()) != null) {
				String []splitLine=line.split(Pattern.quote("|"))
				// Read the string.
				String word=splitLine[0]
				// Read if it is a palindrome.
				Boolean isPalindromeRef =Boolean.valueOf(splitLine[1].trim())
				// Call the palindrome checker function for the string.
				Boolean isPalindromeRes=Palindrome.isPalindrome(word)

				// Check if the result of the function is the expected,
				// according to the file.
				if(isPalindromeRes!=isPalindromeRef)
				{
					failures=true
					break
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		then:
		failures!=true

	}
}
