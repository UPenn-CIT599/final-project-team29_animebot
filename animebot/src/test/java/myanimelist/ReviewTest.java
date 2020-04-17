package myanimelist;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ReviewTest {

	@Test
	void testGetHelpfulCount() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals(2078, anime.getReviews().get(0).getHelpfulCount());
	}

	@Test
	void testGetReviewer() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals("Archaeon", anime.getReviews().get(0).getReviewer().getUsername());
	}

	@Test
	void testGetUrl() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals("https://myanimelist.net/reviews.php?id=43359", anime.getReviews().get(0).getUrl());
	}

	@Test
	void testGetContent() {
		Anime anime = MyAnimeList.getAnime(9253);
		assertEquals("\"People assume that time is a strict progression of cause to effect, but actually, "
				+ "from a non-linear, non subjective viewpoint it's more like a big ball of wibbly wobbley ... "
				+ "timey wimey ... stuff\" - The Doctor\r\n"
				+ "When it comes to entertainment, one of the easiest things to get wrong is the concept of "
				+ "time travel. Part of the reason for this is because our scientific understanding of \"reality\" "
				+ "is still in its infancy, and this means that the writers for shows like Doctor Who, Quantum Leap, "
				+ "even Star Trek, must apply their imagination and creativity in order to resolve some of the "
				+ "inherent paradoxes thatwill occur whenever a narrative decides to hop, skip or jump.\\n\\n\r\n"
				+ "In other words, they make it up.\\n\\n\r\n"
				+ "More often than not the implausibility of any sort of time travel is camouflaged with "
				+ "pseudo-science, techno-babble, and a good smattering of conversational quantum-hokum. "
				+ "These add a veneer of believability so that the average person can achieve the suspension "
				+ "of disbelief required to buy into the storyline. When it comes to anime though, the concept "
				+ "of travelling through time has generally lacked in substance, delivery, and even narrative "
				+ "relevance.\\n\\n\r\n" + "Until now ...\\n\\n\r\n"
				+ "Originally a visual novel by 5pb and Nitroplus, Steins;Gate tells the story of Okabe Rintaro "
				+ "(the self styled mad scientist known as Hououin Kyouma), and his \"colleagues\" at the Future "
				+ "Gadget Laboratory, Hashida \"Daru\" Itaru and Shiina Mayuri. Okabe spends his days making strange "
				+ "inventions with Daru, and the oddest one so far is the Phone Microwave [name subject to change]. "
				+ "At first it seems as though this device does nothing more than turn bananas into a green, jelly-like "
				+ "substance, but it has a hidden side effect that no one knows about. Everything seems placid and "
				+ "normal until the day that Okabe and Mayuri decide to attend a lecture given by the eminent Professor "
				+ "Nakabachi on the subject of time machines and time travel.\\n\\n\r\n"
				+ "For the most part Steins;Gate is a surprisingly well thought out series that applies the notion of "
				+ "cause and effect in a reasonably intelligent manner. The plot follows a logical, if somewhat "
				+ "timeworn progression, and while there are numerous recycles, repeats, reboots, and \"do overs\" "
				+ "that form an integral part of any time travel tale, these are handled in a way that would have "
				+ "turned Endless Eight from tedium incarnate into an arc that was at least watchable. The show "
				+ "throws around a number of concepts and theories to explain or justify certain  aspects of the "
				+ "science fiction, and on quite a few occasions these have been woven into the main body of the "
				+ "plot very well. Ideas like the Butterfly Effect (which, given the visual cues, should be obvious "
				+ "to anyone), the Observer Effect and Schrödinger's Cat have been used to support the problems "
				+ "caused by time travel (and their resolution), and in that respect Steins;Gate deserves a good "
				+ "deal of praise for trying to use science to support the science fiction (and it does it far "
				+ "better than the likes of Puella Magi Madoka Magica).\\n\\n\r\n" 
				+ "Unfortunately it's not all fun and games.\\n\\n\r\n" 
				+ "One of the problems within the narrative is the inevitable conflict between human drama and "
				+ "science fiction, and in true anime fashion the emotional side wins out. This has the unfortunate "
				+ "effect of removing much of the chaos that is inherent in a story about time travel, and replacing "
				+ "it with predictability and melodrama. Thankfully the human side of the tale is handled in a "
				+ "surprisingly decent manner, but this is tempered by the fact that a number of basic questions "
				+ "are never actually addressed. The very nature of this anime automatically requires that certain "
				+ "aspects be resolved or explained, and while there are all sorts of \"scientific\" reasons flying "
				+ "around, the series tends to shy away from tackling certain first order issues like the "
				+ "Grandfather Paradox.\\n\\n\r\n" + 
				"There's also the matter of the rather \"neat\" ending, but we'll get to that in a bit.\\n\\n\r\n" + 
				"Steins;Gate is a very good looking series, but as with any adaptation from another visual medium, "
				+ "there's an automatic limitation placed on aspects like character design. That said, White Fox "
				+ "have produced a series that viewers may find appealing, if a little generic at times, and have "
				+ "used what they've been given to very good effect. The character animation is of a high standard, "
				+ "and many of the visual effects are imaginative and well choreographed (which should be no "
				+ "surprise given that White Fox also produced Tears to Tiara and Katanagatari). It's unfortunate "
				+ "then, that the typical anime mentality comes to the fore in the little details, the main one being "
				+ "the distinct lack of variety where clothing is concerned. Everyone seems to have only one outfit, "
				+ "which may seem a little picky to some, but imagine how you would feel if you wore the same "
				+ "underwear for three weeks while running around and in mostly warm weather.\\n\\n\r\n"  
				+ "This mentality also comes to the fore in the script, and while the majority of the dialogue in "
				+ "the show is actually pretty good, the usual shenanigans come out to play at times when there really "
				+ "doesn't need to be any more drama. Thankfully the voice actors are experienced enough to know how to "
				+ "deal with the scriptwriters' attempts at overcompensating for various shortcomings, and in truth they're "
				+ "the ones that carry this series. If it wasn't for the talents of Miyano Mamoru, Imai Asami, Seki Tomokazu, "
				+ "Hanazawa Kana, and the rest of the cast, Steins;Gate would quickly collapse under its own weight, and "
				+ "it's thanks to the seiyuu's abilities that the more technical or scientific portions of the script can "
				+ "be delivered in a manner that fits with the narrative.\\n\\n\r\n" + 
				"The opening sequence features Hacking The Gate by Ito Kanako, a fairly average J-pop track track that "
				+ "has been set to a montage of most of the characters who seem to be deep in contemplation while a "
				+ "variety of clock faces, cogs and technical looking diagrams zip around the screen. On the other hand, "
				+ "The Twelve Time Governing Covenants by Sakakibara Yui works rather well as the  closing theme, and "
				+ "for the most part the end sequence is a far more subtle and off kilter affair that is more in tune "
				+ "with the atmosphere of the series proper (until the last few seconds that is, and one has to wonder "
				+ "about the mentality of the person who thought ending the sequence like that was a good idea).\\n\\n\r\n"  
				+ "As for the background music, there's a rather nice variety of tracks that are often very subtly used. "
				+ "More often than not the series relies on mundane noises and silence, and because of that attention has "
				+ "been paid to the timing and usage of the score.\\n\\n\r\n" + 
				"Steins;Gate has a core set of characters who are surprisingly well defined from the start of the series, "
				+ "but in terms of overall development much of the growth applies only to Okabe. Now this isn't really "
				+ "surprising given the events in the story, and to be honest the show is actually better with his "
				+ "character being the only one who truly changes. Okabe's development when dealing with the events that "
				+ "are rapidly spiralling out of control is handled in a sensitive yet realistic manner, and it's nice "
				+ "to see that the anime hasn't shied away from depicting the apathy he feels after experiencing a series "
				+ "of personally harrowing events.\\n\\n\r\n" + 
				"Unfortunately the attempts to further develop some of the other characters tend to fall a little short of "
				+ "the mark, and this leads to a few situations that effectively remove the dramatic tension that has been "
				+ "painstakingly built up. The sad part is that while it's laudable to try and develop characters like Suzu, "
				+ "Mayuri, Feyris, and even Tennouji Yugo (Mr Braun), this should never come at the detriment of the main "
				+ "storyline.\\n\\n\r\n" + 
				"Steins;Gate is a very entertaining series that isn't afraid to play around with various scientific concepts, "
				+ "but at the same time it clearly avoids tackling certain major issues related to time travel, and the focus "
				+ "on human drama can sometimes be at odds with the events in the storyline. That said, it's a very enjoyable "
				+ "anime that doesn't get too bogged down in technicalities, and while I rather liked the fact that Okabe cast "
				+ "himself in the role of mad scientist (complete with laugh), imagine my surprise at finding out he's "
				+ "supposed to be 18 years old.\\n\\n\r\n" + 
				"Which brings us back to the ending.\\n\\n\r\n" + 
				"There's a certain ... \"clinical\" ... feel to the conclusion that really doesn't sit too well, and while "
				+ "it's always nice to see a story end happily, one has to wonder about the plausibility of it all. The thing "
				+ "is, Steins;Gate uses a concept of time travel similar to that used in Quantum Leap, and therein lies the problem. "
				+ "Anyone who is familiar with the latter series knows that Dr Sam Beckett (an actual doctor, not an 18 year "
				+ "old first year student like Okabe), is unable to return to his original timeline because too many changes "
				+ "have been made to past events. In the nomenclature of Steins;Gate, he's moved across too many world lines "
				+ "and affected too many lives, and this is one of the major things that Steins;Gate glosses over completely. "
				+ "Now one could argue that the idea used in the series creates an effective escape clause, but that only "
				+ "covers certain people. The simple fact is that everyone you meet when you travel through time, no matter "
				+ "how brief the contact, is affected by your presence, so in order to return to one's original timeline, "
				+ "one must undo every contact with every person, even down to brushing shoulders with a total stranger on "
				+ "the street.\\n\\n\r\n" + 
				"One of the other aspects that really should have been explored is Okabe's ability, Reading Steiner. At no "
				+ "point does the series delve into why he has this ability or how it works, and this is more than a little "
				+ "odd given how much importance is placed on \"Fool yourself. Fool the world\".\\n\\n\r\n" + 
				"Even with those issues though, Steins;Gate is easily one of the better science fiction anime to appear in "
				+ "the last few years, and while there are areas that could have been improved upon, the whole is greater "
				+ "than the sum of its parts. Steins;Gate is an enjoyable romp in the realms of implausibility that delivers "
				+ "on several levels, and while the happy ending may not sit well with everyone, the conclusion to the story "
				+ "does offer a degree of catharsis.\\n\\n\r\n"  
				+ "It's just a shame that everyone seems to think you need a happy ending in order to make a story great.",
				anime.getReviews().get(0).getContent());
	}
}
