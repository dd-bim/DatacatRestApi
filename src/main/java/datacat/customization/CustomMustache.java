// package datacat.customization;

// // =====================================================================================================================
// // I M P O R T   S E C T I O N
// // =====================================================================================================================
// import java.io.IOException;
// import java.io.Writer;
// import com.github.mustachejava.Mustache;

// // =====================================================================================================================
// // C U S T O M   M U S T A C H E   S E C T I O N
// // this class is used to include custom methods for Mustache template engine
// // =====================================================================================================================
// public class CustomMustache implements Lambda {
    
//     // Method to capitalize the first letter
//     public static String capitalizeFirstLetter(String input) {
//         if (input == null || input.isEmpty()) {
//             return input;
//         }
//         return input.substring(0, 1).toUpperCase() + input.substring(1);
//     }

// }