package ar.edu.utn.frba.dds.model.entities.solicitudes;

import java.util.List;

public class DetectorDeSpamInteligente implements DetectorDeSpam {

  private static final List<String> PALABRAS_SPAM = List.of(
      "gratis", "oferta", "promoción", "haz clic", "click aquí",
      "dinero rápido", "gana", "millones", "suscríbete", "compra ahora",
      "regalo", "premio", "descuento", "sexo", "bitcoin", "casino",
      "credit", "urgent", "password", "limited time", "sale"
  );

  @Override
  public boolean esSpam(String texto) {
    if (texto == null || texto.isBlank()) {
      return false;
    }

    String t = texto.toLowerCase().trim();
    double score = 0;

    // 1️⃣ Palabras sospechosas
    for (String palabra : PALABRAS_SPAM) {
      if (t.contains(palabra)) {
        score += 2;
      }
    }

    // 2️⃣ Exceso de mayúsculas
    long mayusculas = texto.chars().filter(Character::isUpperCase).count();
    double proporcionMayus = (double) mayusculas / texto.length();
    if (proporcionMayus > 0.3) {
      score += 2;
    }

    // 3️⃣ Exceso de signos de exclamación o símbolos
    long simbolos = texto.chars().filter(ch -> "!$%&@#".indexOf(ch) != -1).count();
    if (simbolos > 3) {
      score += 1.5;
    }

    // 4️⃣ Repetición de palabras
    String[] palabras = t.split("\\s+");
    if (palabras.length > 5) {
      long repetidas = contarRepetidas(palabras);
      if (repetidas > 2) {
        score += 1.5;
      }
    }

    // 5️⃣ Texto muy largo sin sentido
    if (texto.length() > 500 && simbolos > 10) {
      score += 1.5;
    }

    // 6️⃣ Palabras con formato sospechoso (como g@na o d1nero)
    if (t.matches(".*[0-9@#$%]{2,}.*")) {
      score += 2;
    }

    // 🧮 Umbral de spam
    return score >= 3.5;
  }

  private long contarRepetidas(String[] palabras) {
    long repetidas = 0;
    for (int i = 0; i < palabras.length - 1; i++) {
      if (palabras[i].equals(palabras[i + 1])) {
        repetidas++;
      }
    }
    return repetidas;
  }
}
