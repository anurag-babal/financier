class User {
  final int id;
  final String name;
  final String username;
  final String email;
  final String profilePictureUrl;
  final String? bio;
  final String? currency;
  final double? monthlyBudget;

  User({
    required this.id,
    required this.name,
    required this.username,
    required this.email,
    required this.profilePictureUrl,
    this.bio,
    this.currency,
    this.monthlyBudget,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'],
      name: json['name'],
      username: json['username'],
      email: json['email'],
      profilePictureUrl: json['profilePictureUrl'] ?? '',
      bio: json['bio'],
      currency: json['currency'],
      monthlyBudget: json['monthlyBudget'] != null ? (json['monthlyBudget'] as num).toDouble() : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'username': username,
      'email': email,
      'profilePictureUrl': profilePictureUrl,
      'bio': bio,
      'currency': currency,
      'monthlyBudget': monthlyBudget,
    };
  }
}
