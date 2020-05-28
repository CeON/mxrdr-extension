package pl.edu.icm.pl.mxrdr.extension.importer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SymmetryStructureMapper {
    private static Map<String, String> MAPPING = Initializer.createStructureMapping();

    public static String map(String value) {
        return MAPPING.getOrDefault(value.trim(), "Other");
    }

    private static class Initializer {
        static Map<String, String> createStructureMapping() {
            HashMap<String, String> mapping = new HashMap<>();
            mapping.put("P 1", "1. P1");
            mapping.put("P 1 2 1", "3. P2");
            mapping.put("P 1 21 1", "4. P2(1)");
            mapping.put("C 1 2 1", "5. C2");
            mapping.put("P 2 2 2", "16. P222");
            mapping.put("P 2 2 21", "17. P222(1)");
            mapping.put("P 21 21 2", "18. P2(1)2(1)2");
            mapping.put("P 21 21 21", "19. P2(1)2(1)2(1)");
            mapping.put("C 2 2 21", "20. C222(1)");
            mapping.put("C 2 2 2", "21. C222");
            mapping.put("F 2 2 2", "22. F222");
            mapping.put("I 2 2 2", "23. I222");
            mapping.put("I 21 21 21", "24. I2(1)2(1)2(1)");
            mapping.put("P 4", "75. P4");
            mapping.put("P 41", "76. P4(1)");
            mapping.put("P 42", "77. P4(2)");
            mapping.put("P 43", "78. P4(3)");
            mapping.put("I 4", "79. I4");
            mapping.put("I 41", "80. I4(1)");
            mapping.put("P 4 2 2", "89. P422");
            mapping.put("P 4 21 2", "90. P42(1)2");
            mapping.put("P 41 2 2", "91. P4(1)22");
            mapping.put("P 41 21 2", "92. P4(1)2(1)2");
            mapping.put("P 42 2 2", "93. P4(2)22");
            mapping.put("P 42 21 2", "94. P4(2)2(1)2");
            mapping.put("P 43 2 2", "95. P4(3)22");
            mapping.put("P 43 21 2", "96. P4(3)2(1)2");
            mapping.put("I 4 2 2", "97. I422");
            mapping.put("I 41 2 2", "98. I4(1)22");
            mapping.put("P 3", "143. P3");
            mapping.put("P 31", "144. P3(1)");
            mapping.put("P 32", "145. P3(2)");
            mapping.put("R 3", "146. R3");
            mapping.put("P 3 1 2", "149. P312");
            mapping.put("P 3 2 1", "150. P321");
            mapping.put("P 31 1 2", "151. P3(1)12");
            mapping.put("P 31 2 1", "152. P3(1)21");
            mapping.put("P 32 1 2", "153. P3(2)12");
            mapping.put("P 32 2 1", "154. P3(2)21");
            mapping.put("R 3 2", "155. R32");
            mapping.put("P 6", "168. P6");
            mapping.put("P 61", "169. P6(1)");
            mapping.put("P 65", "170. P6(5)");
            mapping.put("P 62", "171. P6(2)");
            mapping.put("P 64", "172. P6(4)");
            mapping.put("P 63", "173. P6(3)");
            mapping.put("P 6 2 2", "177. P622");
            mapping.put("P 61 2 2", "178. P6(1)22");
            mapping.put("P 65 2 2", "179. P6(5)22");
            mapping.put("P 62 2 2", "180. P6(2)22");
            mapping.put("P 64 2 2", "181. P6(4)22");
            mapping.put("P 63 2 2", "182. P6(3)22");
            mapping.put("P 2 3", "195. P23");
            mapping.put("F 2 3", "196. F23");
            mapping.put("I 2 3", "197. I23");
            mapping.put("P 21 3", "198. P2(1)3");
            mapping.put("I 21 3", "199. I2(1)3");
            mapping.put("P 4 3 2", "207. P432");
            mapping.put("P 42 3 2", "208. P4(2)32");
            mapping.put("F 4 3 2", "209. F432");
            mapping.put("F 41 3 2", "210. F4(1)32");
            mapping.put("I 4 3 2", "211. I432");
            mapping.put("P 43 3 2", "212. P4(3)32");
            mapping.put("P 41 3 2", "213. P4(1)32");
            mapping.put("I 41 3 2", "214. I4(1)32");
            return Collections.unmodifiableMap(mapping);
        }
    }
}