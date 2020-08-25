
  Pod::Spec.new do |s|
    s.name = 'InlocoCapacitorPlugin'
    s.version = '0.1.0'
    s.summary = 'to define'
    s.license = 'MIT'
    s.homepage = 'https://github.com/In-Loco-Media/inloco-capacitor-plugin.git'
    s.author = 'Inloco'
    s.source = { :git => 'https://github.com/In-Loco-Media/inloco-capacitor-plugin.git', :tag => s.version.to_s }
    s.source_files = 'ios/Plugin/**/*.{swift,h,m,c,cc,mm,cpp}'
    s.ios.deployment_target  = '11.0'
    s.dependency 'Capacitor'
    s.dependency 'InLocoEngage-iOS-SDK', '5.3.0'
    s.static_framework = true
  end
