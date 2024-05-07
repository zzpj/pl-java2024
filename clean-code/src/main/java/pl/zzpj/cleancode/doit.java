package pl.zzpj.cleancode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class doit {

	Map<Integer, Integer> __1 = new HashMap<Integer, Integer>();
	private int _2 = Integer.MIN_VALUE;private int kpp = Integer.MAX_VALUE;
	
	public doit(List<Integer> i1) {
		p(i1);
	}
	
	public doit() {

		/*Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla molestie lacus at diam congue fermentum. Phasellus id facilisis dolor. Maecenas neque purus,
		tempus id scelerisque nec, facilisis eget nunc. Praesent ante dolor, maximus et porta ac, finibus vel erat. Donec diam orci, bibendum eu odio ut, auctor fringilla justo.
//		Nam sit amet ante tempus, varius nisi id, porttitor mauris. Suspendisse vitae vulputate ipsum.
//
//Quisque malesuada lacus nec arcu posuere facilisis. Praesent vel vulputate metus. Vestibulum ac maximus magna. Aliquam lacinia iaculis erat, ac ullamcorper lectus cursus vel.
//Vivamus id pulvinar risus. Sed in nunc volutpat, sollicitudin lectus a, faucibus magna. Sed maximus fringilla nibh id porttitor. Quisque dignissim, leo ut consectetur hendrerit, ex
//elit feugiat nisi, nec elementum elit felis id dolor. Maecenas lacinia consequat lacus. Nulla vitae erat a justo pellentesque eleifend id nec massa. Morbi feugiat ut erat ac placerat.
//Nulla lorem metus, placerat quis blandit in, aliquet ac ipsum. Etiam ut arcu sem. Aliquam erat volutpat.
//
//Quisque tellus dui, semper efficitur efficitur ut, accumsan sed nulla. Nullam sed orci placerat, auctor est sed, venenatis felis. In vulputate mollis ante, sed
//tincidunt nisi maximus nec. Nunc id purus non lacus rutrum pharetra non ut libero. Integer id tellus in dolor feugiat iaculis. Phasellus id magna id augue tempus p
//orta in quis justo. Curabitur eleifend id sem ac faucibus. Vivamus consequat ac enim at dapibus. Cras in volutpat dolor. Pellentesque viverra, metus ac consectetur congue, mi ligula
// vestibulum mi, ut sagittis augue justo eget dolor. Vivamus sit amet pharetra leo.
//
//Vestibulum consectetur nunc metus, lobortis dapibus lacus porta in. Morbi et metus massa. Donec cursus efficitur massa, non egestas ante convallis et. Aliquam matti
//s arcu in felis rutrum vehicula. Nunc aliquam nulla sit amet vestibulum tincidunt. Maecenas ultricies augue ut nisi blandit vestibulum. Nunc finibus at quam a tincidunt. Nam
//tristique nisl eget lacus dapibus, eleifend fermentum risus facilisis. Integer viverra eros eget mi ultricies consequat.
//
//Phasellus mattis commodo magna, id semper sapien suscipit a. Vivamus ut condimentum mauris. Praesent aliquet erat fringilla velit finibus porta. In molestie lacus
//elementum eros tempus, et lacinia magna interdum. Suspendisse mattis vehicula elit, a consequat lorem accumsan sit amet. Vivamus molestie lectus et lacus pharetra, ut
//pulvinar est laoreet. Vivamus hendrerit pellentesque blandit. Curabitur sed dolor iaculis, sodales nisl id, tempor metus. Vivamus erat sem, iaculis vitae risus sed, eleifend luctus velit.
*/
	}
	
	public void p(List<Integer> l1) { int i = 0;for (; i < l1.size(); i++) { p(l1.get(i)); }}
	
	public void p(Integer i) {
		if (__1.containsKey(i)) {
			Integer k = __1.get(i);
			__1.put(i, k +1);
		} else {
			__1.put(1, 1);
		}
		
		if (i > _2) {
			_2 = i;
		}
		
		if (i < kpp) {
			kpp = i;
		}
	}
	
	public int DOIT(int i) { if (__1.containsKey(i)) { return __1.get(i);
		} else { return 0;
		} }
	
	public double dasIstGut() {
		double jk = 0;double p = 0;
		for (Entry<Integer, Integer> u : __1.entrySet()) {
			p += u.getValue();jk += u.getKey() * u.getValue();
		}return jk/p;
	}
	
	public int l1() {
		return _2;
	}
	
	public int l2() {
		return kpp;
	}


	//TODO: future use when i will know what  my name and number are
	public String getFizzBuzzNumber(int number) {
		//TODO: implement
		//Das Belvedere ist ein Barock-Palast.
		//Heute ist es ein Museum.
		//Die Menschen fotografieren oft das Belvedere.
		//Das Museum ist interessant, aber es gibt auch einen schönen Garten.
		return null;
	}
}