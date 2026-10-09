import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      debugShowCheckedModeBanner: false,
      home: CalculatorScreen(),
    );
  }
}

class CalculatorScreen extends StatefulWidget {
  const CalculatorScreen({super.key});

  @override
  State<CalculatorScreen> createState() => _CalculatorScreenState();
}

class _CalculatorScreenState extends State<CalculatorScreen> {
  final TextEditingController _num1Controller = TextEditingController();
  final TextEditingController _num2Controller = TextEditingController();
  String _result = "";

  void _calculate(String op) {
    double? n1 = double.tryParse(_num1Controller.text);
    double? n2 = double.tryParse(_num2Controller.text);

    if (n1 == null || n2 == null) {
      setState(() => _result = "Vui lòng nhập số hợp lệ");
      return;
    }

    double res = 0;
    if (op == '+') res = n1 + n2;
    if (op == '-') res = n1 - n2;
    if (op == '*') res = n1 * n2;
    if (op == '/') {
      if (n2 == 0) {
        setState(() => _result = "Không thể chia cho 0");
        return;
      }
      res = n1 / n2;
    }

    setState(() {
      _result = res % 1 == 0 ? res.toInt().toString() : res.toString();
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(24.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const Center(
                child: Text(
                  "Thực hành 03",
                  style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                ),
              ),
              const SizedBox(height: 30),
              TextField(
                controller: _num1Controller,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                ),
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _buildOpBtn("+", Colors.redAccent, () => _calculate("+")),
                  _buildOpBtn("-", Colors.amber.shade700, () => _calculate("-")),
                  _buildOpBtn("*", Colors.indigo, () => _calculate("*")),
                  _buildOpBtn("/", Colors.black, () => _calculate("/")),
                ],
              ),
              const SizedBox(height: 16),
              TextField(
                controller: _num2Controller,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(
                  border: OutlineInputBorder(borderRadius: BorderRadius.circular(12)),
                ),
              ),
              const SizedBox(height: 24),
              Text(
                "Kết quả: $_result",
                style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildOpBtn(String label, Color color, VoidCallback onTap) {
    return SizedBox(
      width: 65,
      height: 45,
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: color,
          foregroundColor: Colors.white,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          padding: EdgeInsets.zero,
        ),
        onPressed: onTap,
        child: Text(label, style: const TextStyle(fontSize: 20)),
      ),
    );
  }
}