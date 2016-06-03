/**
 * 
 */
package jav.ocho.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Maximo
 *
 */
public class ExampleJava {
	
	static class Persona {
		String nombre;
		String apellido;
		int anos;
		
		public Persona(String nombre, String apellido, int anos) {
			this.nombre = nombre;
			this.apellido = apellido;
			this.anos = anos;
		}

		public String getApellidos() {
			return apellido;
		}

		public int getAnos() {
			return anos;
		}

		public String getNombre() {
			return nombre;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Lambdas
		String[] cadenas = new String[] { "max", "uno", "max1", "max2", "max3",
				"max4", "max5", "dos", "maxtres" };
		Predicate<String> pre = s -> s.startsWith("max");
		Predicate<String> pre1 = s -> s.endsWith("tres");
		List<String> s = new ArrayList<String>(Arrays.asList(cadenas));
		s.removeIf(pre.and(pre1));
		System.out.println(s);

		BinaryOperator<Integer> sum = (a, b) -> Integer.sum(a, b);
		BinaryOperator<Integer> sum1 = Integer::sum;

		Consumer<String> print = (a) -> System.out.println(a);
		Consumer<String> print1 = System.out::println;
		print.accept("Suma expresion lambda 2 + 3: " + sum.apply(2, 3));
		print1.andThen(print).accept(
				"Suma metodos referencia 4 + 5: " + sum1.apply(4, 5));

		Comparator<String> compare = (a, b) -> a.compareToIgnoreCase(b);
		Comparator<String> compare1 = String::compareToIgnoreCase;
		print.accept("Comparamos expresion lambda max con max: "
				+ compare.compare("max", "max"));
		print1.accept("Comparamos metodos referencia max1 con max: "
				+ compare1.compare("max1", "max") + "");

		Supplier<List<String>> listSupplier = () -> new ArrayList<String>();
		List<String> lista = listSupplier.get();
		Supplier<List<String>> listSupplier1 = ArrayList<String>::new;
		List<String> lista1 = listSupplier1.get();
		lista.add("añado1");
		lista1.add("añado2");
		print.accept("Instanciamos una lista expresion lambda: " + lista);
		print1.accept("Instanciamos una lista metodos referencia: " + lista1);

		Function<Integer, List<String>> listSupplier2 = (num) -> new ArrayList<String>( num );
		List<String> lista2 = listSupplier2.apply(1);
		Function<Integer, List<String>> listSupplier3 = ArrayList<String>::new;
		List<String> lista3 = listSupplier3.apply(1);
		lista2.add("hola1");
		lista3.add("hola2");
		print.accept("Instanciamos una lista con parametro expresion lambda: "
				+ lista2);
		print1.accept("Instanciamos una lista con parametros metodos referencia: "
				+ lista3);

		// Api Stream
		Object[] obj = Stream.of( "dos", "uno", "tres", "cinco", "cuatro", "seis", "siete", "cinco" )
				.filter( element -> element.startsWith("c") )
				.map( (f) -> f + " concateno" )
				.distinct()
				.sequential().toArray();
		print.accept("Api Stream lambda: " + Arrays.asList(obj));
		
		// Locales Api Stream
		print.accept( "Locales:" );
		List<Locale> locales = Arrays.asList( Locale.getAvailableLocales() );
		for ( Iterator<String> ite = locales.stream()
			.filter(l -> l.toString().matches( "^([a-z]{2})(_[a-zA-Z]{2}){1,2}" ) )
			.map( f -> f.toString().replace("_", "-") )
			.sorted()
			.iterator(); ite.hasNext() ; ) {
				print.accept( ite.next() );
		}
		
		// Bean Api Stream
		print.accept( "Listas con Beans" );
		for ( Iterator<Integer> ite = Stream.of(new Persona("Pedro", "Garcia", 45),
													new Persona("Alejo", "Garcia", 45),
													new Persona("Max", "Garcia", 37), 
													new Persona("Alberto", "Garcia", 41) )
			.filter( p -> p.getAnos() > 35 )
			.mapToInt( Persona::getAnos )
			.iterator(); ite.hasNext() ; ) {
				print.accept( ite.next() + "" );
		};
		
		// Collectors Api Stream
		String cadenaNum = Stream.of( 1, 2, 3, 4, 5, 6)
			.map( Object::toString )
			.collect( Collectors.joining( ", " ) );
		print.accept( "Api Stream Collectors joining: " + cadenaNum );
		
		print.accept( "Listas con Beans" );
		Map<Integer, Map<String, List<Persona>>> personas = Stream.of( new Persona("Pedro", "Garcia", 45),
					new Persona("Alejo", "Garcia", 45),
					new Persona("Max", "Garcia", 37), 
					new Persona("Alberto", "Garcia", 41) )
				.peek( p -> p.getNombre().equals( "Max" ) )
				.collect( Collectors.groupingBy(  Persona::getAnos, Collectors.groupingBy( Persona::getNombre ) ) );
		for ( int ano:  personas.keySet() ) {
			print.accept( "ano: " + ano + " Nombre: " + ((Map<String, List<Persona>>)personas.get( ano )).entrySet().toString() );
		}

	}

}
